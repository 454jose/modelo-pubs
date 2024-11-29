public class Title {
    private int titleId;
    private String titleName;   
    private String titleAuthor; 
    private String type;
    private double price;
    private String pubId;
    private String notes;
    private int titleYear;      

    public Title(int titleId, String titleName, String titleAuthor, String type, double price, String pubId, String notes, int titleYear) {
        this.titleId = titleId;
        this.titleName = titleName;
        this.titleAuthor = titleAuthor;
        this.type = type;
        this.price = price;
        this.pubId = pubId;
        this.notes = notes;
        this.titleYear = titleYear;
    }

    public int getTitleId() { return titleId; }
    public void setTitleId(int titleId) { this.titleId = titleId; }
    
    public String getTitleName() { return titleName; } 
    public void setTitleName(String titleName) { this.titleName = titleName; }  
    
    public String getTitleAuthor() { return titleAuthor; }  // Getter para el autor
    public void setTitleAuthor(String titleAuthor) { this.titleAuthor = titleAuthor; }  // Setter para el autor
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public String getPubId() { return pubId; }
    public void setPubId(String pubId) { this.pubId = pubId; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public int getTitleYear() { return titleYear; }  // Getter para el año
    public void setTitleYear(int titleYear) { this.titleYear = titleYear; }  // Setter para el año

    // Representación como cadena de texto
    @Override
    public String toString() {
        return "Title{" +
                "ID=" + titleId +
                ", Name='" + titleName + '\'' +
                ", Author='" + titleAuthor + '\'' +
                ", Type='" + type + '\'' +
                ", Price=" + price +
                ", PubId='" + pubId + '\'' +
                ", Notes='" + notes + '\'' +
                ", Year=" + titleYear +
                '}';
    }
}
