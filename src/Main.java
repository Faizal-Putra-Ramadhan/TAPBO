import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LibraryManagment pengguna = new LibraryManagment();
        boolean jalan = true;

        while (jalan) {
            System.out.println("\n1. Login");
            System.out.println("2. Daftar");
            System.out.println("3. Keluar");
            System.out.print("Masukkan pilihan: ");

            int pilihan = sc.nextInt();
            sc.nextLine();

            switch (pilihan) {
                case 1 :
                    pengguna.loginPengguna();
                    break;
                case 2 :
                    pengguna.daftarPengguna();
                    break;
                case 3 :
                    jalan = false;
                    break;
                default :
                    System.out.println("Pilihan tidak valid. Coba lagi.");
            }
        }

        sc.close();
    }
}