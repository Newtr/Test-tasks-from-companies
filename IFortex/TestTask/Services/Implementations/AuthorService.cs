using Microsoft.EntityFrameworkCore;
using TestTask.Data;
using TestTask.Models;
using TestTask.Services.Interfaces;

namespace TestTask.Services.Implementations
{
    public class AuthorService : IAuthorService
    {
        private readonly ApplicationDbContext _context;

        public AuthorService(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<Author> GetAuthor()
        {
            return await _context.Books
                .OrderByDescending(b => b.Title.Length)
                .ThenBy(b => b.AuthorId)
                .Include(b => b.Author)
                .Select(b => b.Author)
                .FirstOrDefaultAsync();
        }

        public async Task<List<Author>> GetAuthors()
        {
            var authors = await _context.Authors
                .Where(a =>
                    a.Books.Count(b => b.PublishDate.Year > 2015) > 0 && 
                    a.Books.Count(b => b.PublishDate.Year > 2015) % 2 == 0 
                )
                .ToListAsync();

            return authors;
        }


    }
}
