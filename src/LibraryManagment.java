import java.io.*;
import java.util.*;
public class LibraryManagment extends User{
    private static final Scanner sc = new Scanner(System.in);

    @Override
    public void tampilkanInfo() {
        System.out.println("Pengguna Perpustakaan: " + getNama());
        System.out.println("Saldo: " + getSaldo());
    }



    public void tampilkanInfo(String pesan) {
        System.out.println(pesan);
        tampilkanInfo();
    }

    public void tambahBuku() {
        System.out.print("Masukkan Nama Buku: ");
        String namaBuku = sc.nextLine();
        System.out.print("Masukkan Penulis Buku: ");
        String penulis = sc.nextLine();
        System.out.print("Masukkan Tahun Terbit Buku: ");
        String tahunTerbit = sc.nextLine();
        System.out.print("Masukkan Harga Buku: ");
        long harga = sc.nextLong();
        sc.nextLine(); // Konsum newline

        try {
            FileWriter fw = new FileWriter(getNama() + "_Barang.txt", true);
            fw.write(namaBuku + "," + penulis + "," + tahunTerbit + "," + harga + "\n");
            fw.close();
            System.out.println("Buku berhasil ditambahkan!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menambahkan buku.");
        }
    }


    // Metode untuk menghapus buku
    public void hapusBuku() {
        try {
            File file = new File(getNama() + "_Barang.txt");
            Scanner fileReader = new Scanner(file);
            List<String> buku = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                buku.add(fileReader.nextLine());
            }
            fileReader.close();
            if (buku.size() == 0) {
                System.out.println("Tidak ada buku yang dapat dihapus.");
                return;
            }
            for (int i = 0; i < buku.size(); i++) {
                String[] data = buku.get(i).split(",");
                System.out.println((i + 1) + ". " + data[0] + ", " + data[1] + ", " + data[2] + ", Harga: " + data[3]);
            }
            System.out.print("Masukkan nomor buku yang akan dihapus: ");
            int pilihan = sc.nextInt();
            sc.nextLine(); // Konsum newline
            if (pilihan > 0 && pilihan <= buku.size()) {
                // Menghapus buku berdasarkan pilihan pengguna
                buku.remove(pilihan - 1);

                FileWriter fw = new FileWriter(file);
                for (int i = 0; i < buku.size(); i++) {
                    fw.write(buku.get(i) + "\n");
                }
                fw.close();

                System.out.println("Buku berhasil dihapus!");
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menghapus buku.");
        }
    }


    // Metode untuk melakukan checkout
    public void checkout() {
        try {
            File file1 = new File(getNama() + "_Barang.txt");
            Scanner fileReader1 = new Scanner(file1);
            List<String> buku1 = new ArrayList<>();
            while (fileReader1.hasNextLine()) {
                buku1.add(fileReader1.nextLine());
            }
            fileReader1.close();
            if (buku1.size() == 0) {
                System.out.println("Tidak ada buku yang dapat dihapus.");
                return;
            }
            for (int i = 0; i < buku1.size(); i++) {
                String[] data = buku1.get(i).split(",");
                System.out.println((i + 1) + ". " + data[0] + ", " + data[1] + ", " + data[2] + ", Harga: " + data[3]);
            }
            File file = new File(getNama() + "_Barang.txt");
            Scanner fileReader = new Scanner(file);
            long total = 0;
            List<String> buku = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] data = line.split(",");
                long harga = Long.parseLong(data[3]);
                total += harga;
                buku.add(line);
            }
            fileReader.close();
            if (total == 0) {
                System.out.println("Tidak ada buku yang perlu di-checkout.");
                return;
            }
            System.out.println("Total harga semua buku: " + total);
            System.out.print("Lanjutkan checkout? (Y/N): ");
            char lanjut = sc.next().toLowerCase().charAt(0);
            sc.nextLine(); // Konsum newline
            if (lanjut == 'y') {
                if (getSaldo() >= total) {
                    setSaldo(getSaldo() - total);
                    FileWriter fw = new FileWriter(getNama() + ".txt");
                    fw.write(getNama() + "\n" + getPassword() + "\n" + getSaldo() + "\n");
                    fw.close();
                    new File(getNama() + "_Barang.txt").delete();
                    System.out.println("Checkout berhasil!");
                } else {
                    System.out.println("Saldo tidak cukup untuk melakukan checkout.");
                }
            } else {
                System.out.println("Checkout dibatalkan.");
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat checkout.");
        }
    }


    // Metode untuk melakukan top-up saldo
    public void topUp() {
        System.out.print("Masukkan jumlah saldo yang akan ditambahkan: ");
        long jumlah = sc.nextLong();
        sc.nextLine(); // Konsum newline
        setSaldo(getSaldo() + jumlah);

        try {
            FileWriter fw = new FileWriter(getNama() + ".txt");
            fw.write(getNama() + "\n" + getPassword() + "\n" + getSaldo() + "\n");
            fw.close();
            System.out.println("Top up berhasil! Saldo baru: " + getSaldo());
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat melakukan top up.");
        }
    }

    public void daftarPengguna() {

        System.out.print("Masukkan Nama: ");
        String nama = sc.nextLine();
        System.out.print("Masukkan Password: ");
        String password = sc.nextLine();

        try {
            File file = new File("akun.txt");
            if (!file.exists()) file.createNewFile();


            Scanner fileReader = new Scanner(file);
            boolean ada = false;

            while (fileReader.hasNextLine()) {
                if (fileReader.nextLine().equals(nama)) {
                    ada = true;
                    break;
                }
            }
            fileReader.close();

            if (ada) {
                System.out.println("Nama pengguna sudah ada. Coba nama lain.");
            } else {
                FileWriter fw = new FileWriter("akun.txt", true);
                fw.write(nama + "\n");
                fw.close();

                FileWriter userFile = new FileWriter(nama + ".txt");
                userFile.write(nama + "\n" + password + "\n0\n");
                userFile.close();

                System.out.println("Pendaftaran berhasil!");
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat pendaftaran.");
        }
    }

    public void loginPengguna() {
        System.out.print("Masukkan Nama: ");
        String username = sc.nextLine();
        System.out.print("Masukkan Password: ");
        String password = sc.nextLine();

        try {
            File file = new File(username + ".txt");
            Scanner fileReader = new Scanner(file);

            String namaTersimpan = fileReader.nextLine();
            String passwordTersimpan = fileReader.nextLine();
            long saldo = Long.parseLong(fileReader.nextLine());

            fileReader.close();

            if (namaTersimpan.equals(username) && passwordTersimpan.equals(password)) {
                setNama(username);
                setPassword(password);
                setSaldo(saldo);
                System.out.println("Login berhasil!");
                tampilkanMenu();
            } else {
                System.out.println("Nama pengguna atau password salah.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Pengguna tidak ditemukan.");
        }
    }

    private void tampilkanMenu() {
        boolean jalan = true;

        while (jalan) {
            System.out.println("\nSelamat datang, " + getNama());
            System.out.println("Saldo: " + getSaldo());
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Checkout");
            System.out.println("4. Top Up");
            System.out.println("5. Tampilkan Info");
            System.out.println("6. Logout");
            System.out.print("Masukkan pilihan: ");

            int pilihan = sc.nextInt();
            sc.nextLine();

            switch (pilihan) {
                case 1 :
                    tambahBuku();
                    break;
                case 2 :
                    hapusBuku();
                    break;
                case 3 :
                    checkout();
                    break;
                case 4 :
                    topUp();
                    break;
                case 5 :
                    tampilkanInfo("Info Pengguna:");
                    break;
                case 6 :
                    jalan = false;
                    break;
                default :
                    System.out.println("Pilihan tidak valid. Coba lagi.");
            }
        }
    }

}
