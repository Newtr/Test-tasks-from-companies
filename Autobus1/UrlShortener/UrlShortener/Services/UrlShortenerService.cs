using System.Security.Cryptography;
using Microsoft.EntityFrameworkCore;
using UrlShortener.Data;
using UrlShortener.Models;

namespace UrlShortener.Services;

public class UrlShortenerService
{
    private readonly ApplicationDbContext _context;
    private const int MaxgenerationAttempts = 5;
    private const int ShortCodeLength = 8;

    public UrlShortenerService(ApplicationDbContext context)
    {
        _context = context;
    }

    public async Task<UrlShorter> CreateShortUrls(string originalUrl)
    {
        if (!Uri.IsWellFormedUriString(originalUrl, UriKind.Absolute))
            throw new ArgumentException("Invalid URL format");

        var shortCode = await GenerateUniqueShortCode();

        var shortUrl = new UrlShorter
        {
            OriginalUrl = originalUrl,
            ShortUrl = shortCode,
            CreatedDate = DateTime.UtcNow,
            ClickCount = 0
        };

        await _context.ShortUrls.AddAsync(shortUrl);
        await _context.SaveChangesAsync();

        return shortUrl;
    }

    private async Task<string> GenerateUniqueShortCode()
    {
        for (int i = 0; i < MaxgenerationAttempts; i++)
        {
            var code = GenerateRandomCode(ShortCodeLength);
            if (await IsCodeUnique(code)) return code;
        }

        throw new Exception("Failde to generate unique code");
    }

    private string GenerateRandomCode(int length)
    {
        using var rng = RandomNumberGenerator.Create();
        var byteBuffer = new byte[length];
        rng.GetBytes(byteBuffer);

        return Convert.ToBase64String(byteBuffer)
            .Replace("+", "-")
            .Replace("/", "_")
            .Substring(0, length);
    }

    private async Task<bool> IsCodeUnique(string code)
    {
        return !await _context.ShortUrls
            .AnyAsync(s => s.ShortUrl == code);
    }
}