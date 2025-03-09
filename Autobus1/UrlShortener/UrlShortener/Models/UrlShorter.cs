using System.ComponentModel.DataAnnotations;

namespace UrlShortener.Models
{
    public class UrlShorter
    {
        public int ID { get; set; }
        
        [Required]
        [Url(ErrorMessage = "Некорректный URL")]
        [StringLength(2048, ErrorMessage = "URL не может быть длиннее 2048 символов")]
        public string OriginalUrl { get; set; }
        
        [Required]
        [StringLength(16, ErrorMessage = "Сокращённый URL не может быть длиннее 16 символов")]
        public string ShortUrl { get; set; }
        
        public DateTime CreatedDate { get; set; } = DateTime.UtcNow;
        
        public int ClickCount { get; set; } = 0;
    }
}