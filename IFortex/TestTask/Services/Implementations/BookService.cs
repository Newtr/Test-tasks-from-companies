using Microsoft.EntityFrameworkCore;
using TestTask.Data;
using TestTask.Models;
using TestTask.Services.Interfaces;

namespace TestTask.Services.Implementations
{
    public class BookService : IBookService
    {
        private readonly ApplicationDbContext _context;

        public BookService(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<Book> GetBook()
        {
            return await _context.Books
                .Where(b => b.Title.Contains("Red") && b.PublishDate > new DateTime(2012, 5, 30))
                .FirstOrDefaultAsync();
        }

        public async Task<List<Book>> GetBooks()
        {
            return await _context.Books
                .OrderByDescending(b => b.Price * b.QuantityPublished)
                .Take(1)
                .ToListAsync();
        }
    }
}
