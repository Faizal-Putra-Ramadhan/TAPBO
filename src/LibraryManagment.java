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
        long harga = 0;
        try {
            harga = Long.parseLong(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Harga harus berupa angka. Coba lagi.");
            return;
        }

        // Menulis data ke file
        try (FileWriter fw = new FileWriter("dataBuku.txt", true)) {
            fw.write(namaBuku + "," + penulis + "," + tahunTerbit + "," + harga + "\n");
            System.out.println("Buku berhasil ditambahkan!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menambahkan buku: " + e.getMessage());
        }
    }



    // Metode untuk menghapus buku
    public void hapusBuku() {
        try {
            File file = new File("dataBuku.txt");
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

    public static void lihatInformasiUser() {
        ArrayList<String> akun = new ArrayList<>();
        try {
            File file = new File("akun.txt");
            Scanner fileReader = new Scanner(file);

            // Read the akun.txt file
            while (fileReader.hasNextLine()) {
                akun.add(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Pengguna tidak ditemukan.");
            return;  // Exit the method if the file is not found
        }

        // Print out the user information
        System.out.println("Informasi User:");
        for (int j = 0; j < akun.size(); j++) {
            try {
                File userFile = new File(akun.get(j) + ".txt");
                Scanner userReader = new Scanner(userFile);

                // Read user information from the file
                if (userReader.hasNextLine()) {
                    String namaTersimpan = userReader.nextLine();
                    String passwordTersimpan = userReader.nextLine();
                    long saldo = Long.parseLong(userReader.nextLine());

                    System.out.println((j + 1) + ". Nama: " + namaTersimpan + ", Pass: " + passwordTersimpan );
                }
            } catch (FileNotFoundException e) {
                System.out.println("Pengguna tidak ditemukan untuk akun: " + akun.get(j));
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan saat membaca informasi pengguna: " + akun.get(j));
            }
        }
    }

    public void menuAdmin(){
        boolean ulang = true;
        while(ulang){
            int pilihan;
            System.out.println("Menu Admin");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Lihat Informasi User");
            System.out.println("4. Logout");
            System.out.print("Masukan pilihan anda : ");
            pilihan = sc.nextInt();
            sc.nextLine();

            if(pilihan == 1){
                tambahBuku();
            }else if(pilihan == 2){
                hapusBuku();
            }else if(pilihan == 3){
                lihatInformasiUser();
            }else if(pilihan == 4){
                ulang = false;
            }else{
                System.out.println("Pilihan anda tidak valid!!");
            }
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

        if(username.equals("admin") && password.equals("admin")){
            menuAdmin();
            return;
        }

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

    public void tambahPembelian() {
        try {
            File file = new File("dataBuku.txt");
            Scanner fileReader = new Scanner(file);
            List<String> buku = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                buku.add(fileReader.nextLine());
            }
            fileReader.close();

            if (buku.size() == 0) {
                System.out.println("Tidak ada buku yang tersedia.");
                return;
            }

            System.out.println("Daftar Buku Tersedia:");
            for (int i = 0; i < buku.size(); i++) {
                String[] data = buku.get(i).split(",");
                System.out.println((i + 1) + ". " + data[0] + ", " + data[1] + ", " + data[2] + ", Harga: " + data[3]);
            }

            System.out.print("Pilih nomor buku yang ingin ditambahkan ke keranjang: ");
            int pilihan = sc.nextInt();
            sc.nextLine(); // Konsum newline

            if (pilihan > 0 && pilihan <= buku.size()) {
                String selectedBook = buku.get(pilihan - 1);

                // Menambahkan buku yang dipilih ke dalam file pembelian pengguna
                File userCartFile = new File(getNama() + "_Barang.txt");
                FileWriter fw = new FileWriter(userCartFile, true);
                fw.write(selectedBook + "\n");
                fw.close();

                System.out.println("Buku berhasil ditambahkan ke keranjang.");
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menambah buku ke keranjang.");
        }
    }

    public void hapusPembelian() {
        try {
            File userCartFile = new File(getNama() + "_Barang.txt");
            Scanner fileReader = new Scanner(userCartFile);
            List<String> pembelian = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                pembelian.add(fileReader.nextLine());
            }
            fileReader.close();

            if (pembelian.size() == 0) {
                System.out.println("Keranjang Anda kosong.");
                return;
            }

            System.out.println("Daftar Buku di Keranjang:");
            for (int i = 0; i < pembelian.size(); i++) {
                String[] data = pembelian.get(i).split(",");
                System.out.println((i + 1) + ". " + data[0] + ", " + data[1] + ", " + data[2] + ", Harga: " + data[3]);
            }

            System.out.print("Pilih nomor buku yang ingin dihapus dari keranjang: ");
            int pilihan = sc.nextInt();
            sc.nextLine(); // Konsum newline

            if (pilihan > 0 && pilihan <= pembelian.size()) {
                // Menghapus buku berdasarkan pilihan pengguna
                pembelian.remove(pilihan - 1);

                // Menulis ulang daftar pembelian ke file
                FileWriter fw = new FileWriter(userCartFile);
                for (int i = 0; i < pembelian.size(); i++) {
                    fw.write(pembelian.get(i) + "\n");
                }
                fw.close();

                System.out.println("Buku berhasil dihapus dari keranjang.");
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menghapus buku dari keranjang.");
        }
    }

    private void tampilkanMenu() {
        boolean jalan = true;

        while (jalan) {
            System.out.println("\nSelamat datang, " + getNama());
            System.out.println("Saldo: " + getSaldo());
            System.out.println("1. Tambah Pembelian");
            System.out.println("2. Hapus Pembelian");
            System.out.println("3. Checkout");
            System.out.println("4. Top Up");
            System.out.println("5. Tampilkan Info");
            System.out.println("6. Logout");
            System.out.print("Masukkan pilihan: ");

            int pilihan = sc.nextInt();
            sc.nextLine();

            switch (pilihan) {
                case 1 :
                    tambahPembelian();
                    break;
                case 2 :
                    hapusPembelian();
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
