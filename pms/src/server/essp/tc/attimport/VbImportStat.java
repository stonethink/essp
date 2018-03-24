package server.essp.tc.attimport;

public class VbImportStat {
    private String total;
    private String imported;
    private String importedNoAtt;
    private String importedAtt;
    public VbImportStat() {
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setImported(String imported) {
        this.imported = imported;
    }

    public void setImportedNoAtt(String importedNoAtt) {
        this.importedNoAtt = importedNoAtt;
    }

    public void setImportedAtt(String importedAtt) {
        this.importedAtt = importedAtt;
    }

    public String getTotal() {
        return total;
    }

    public String getImported() {
        return imported;
    }

    public String getImportedNoAtt() {
        return importedNoAtt;
    }

    public String getImportedAtt() {
        return importedAtt;
    }
}
