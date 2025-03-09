using Microsoft.EntityFrameworkCore;
using UrlShortener.Models;

namespace UrlShortener.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) 
            : base(options) { }
        
        public DbSet<UrlShorter> ShortUrls { get; set; }
        
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            
            modelBuilder.Entity<UrlShorter>()
                .HasIndex(u => u.ShortUrl)
                .IsUnique();
        }
    }
}