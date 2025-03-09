using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using UrlShortener.Data;
using UrlShortener.Models;
using UrlShortener.Services;

namespace UrlShortener.Controllers
{
    public class UrlShortenerController : Controller
    {
        private readonly ApplicationDbContext _context;
        private readonly UrlShortenerService _urlShortenerService;

        public UrlShortenerController(ApplicationDbContext context, UrlShortenerService urlShortenerService)
        {
            _context = context;
            _urlShortenerService = urlShortenerService;
        }
        
        public async Task<IActionResult> Index()
        {
            var urls = await _context.ShortUrls
                .OrderByDescending(u => u.CreatedDate)
                .ToListAsync();
            return View(urls);
        }
        
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Delete(int id)
        {
            var url = await _context.ShortUrls.FindAsync(id);
            if (url != null)
            {
                _context.ShortUrls.Remove(url);
                await _context.SaveChangesAsync();
            }
            return RedirectToAction(nameof(Index));
        }
        
        public async Task<IActionResult> Create(int? id)
        {
            if (id == null) 
                return View(new UrlShorter());

            var url = await _context.ShortUrls.FindAsync(id);
            if (url == null)
                return NotFound();

            return View(url);
        }
        
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create(int? id, string originalUrl)
        {
            if (string.IsNullOrWhiteSpace(originalUrl))
            {
                ModelState.AddModelError("", "Введите URL");
                return View();
            }
            
            if (!Uri.IsWellFormedUriString(originalUrl, UriKind.Absolute))
            {
                ModelState.AddModelError("", "Неверный формат URL");
                return View(new UrlShorter { OriginalUrl = originalUrl });
            }

            if (!id.HasValue || id.Value == 0)
            {
                try
                {
                    var newUrl = await _urlShortenerService.CreateShortUrls(originalUrl);
                    _context.ShortUrls.Add(new UrlShorter
                    {
                        OriginalUrl = originalUrl,
                        ShortUrl = newUrl.ShortUrl,
                        CreatedDate = DateTime.UtcNow
                    });
                    await _context.SaveChangesAsync();
                    return RedirectToAction(nameof(Index));
                }
                catch (Exception ex)
                {
                    ModelState.AddModelError("", ex.Message);
                    return View();
                }
            }
            else // Редактирование существующего URL
            {
                var url = await _context.ShortUrls.FindAsync(id);
                if (url == null)
                    return NotFound();

                try
                {
                    url.OriginalUrl = originalUrl;

                    // Генерируем новый короткий URL, если изменился оригинальный
                    if (url.ShortUrl == null || url.OriginalUrl != originalUrl)
                    {
                        var newUrl = await _urlShortenerService.CreateShortUrls(originalUrl);
                        url.ShortUrl = newUrl.ShortUrl;
                    }

                    await _context.SaveChangesAsync();
                    return RedirectToAction(nameof(Index));
                }
                catch (Exception ex)
                {
                    ModelState.AddModelError("", ex.Message);
                    return View(url);
                }
            }
        }
        
        [HttpGet("r/{shortUrl}")]
        public async Task<IActionResult> RedirectToUrl(string shortUrl)
        {
            // Ищем запись по короткому URL
            var url = await _context.ShortUrls.FirstOrDefaultAsync(u => u.ShortUrl == shortUrl);
            if (url == null)
            {
                return NotFound();
            }
            
            // Увеличиваем счётчик переходов
            url.ClickCount++;
            await _context.SaveChangesAsync();
            
            // Перенаправляем пользователя на оригинальный URL
            return Redirect(url.OriginalUrl);
        }
    }
}
