using System.ComponentModel.DataAnnotations;

namespace UrlShortener.Models;

public class UrlShorter
{
    public int ID { get; set; }
    
    [Required]
    [Url]
    [StringLength(2048)]
    public string OriginalUrl { get; set; }
    
    [Required]
    [StringLength(16)]
    public string ShortUrl { get; set; }

    public DateTime CreatedDate { get; set; } = DateTime.UtcNow;

    public int ClickCount { get; set; } = 0;
}