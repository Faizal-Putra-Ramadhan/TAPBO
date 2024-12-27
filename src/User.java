public class User {
    private String nama;
    private String password;
    private long saldo;
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public long getSaldo() {
        return saldo;
    }
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }
    public void tampilkanInfo() {
        System.out.println("Pengguna: " + nama);
        System.out.println("Saldo: " + saldo);
    }
}
