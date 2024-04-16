

package tr.edu.ogu.ceng.model;

public class Transcript {
    // Özellikler
    private String fileName;
    private byte[] content;

    // Kurucu metod
    public Transcript(String fileName, byte[] content) {
        this.fileName = fileName;
        this.content = content;
    }

    // Getter ve setter metotları
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    // Diğer davranışlar ve metotlar...
}
