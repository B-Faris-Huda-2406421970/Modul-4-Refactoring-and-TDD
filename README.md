# Module 4

## Reflection

1. Pertanyaan refleksi dari Percival adalah apakah tes yang kita buat mencakup tiga objektif utama yang perlu dievaluasi saat testing. Ketiga objektif tersebut adalah correctness (kebenaran), maintainability (mudah dijaga), dan productive workflow (flow pekerjaan yang produktif). Menurut saya, flow TDD yang diberikan pada tutorial sudah memenuhi ketiga objektif utama tersebut dan berguna dalam proyek. Tes-tes yang dimasukkan sudah mengcover 100% dari  OrderRepository dan OrderServiceImpl sehingga dari segi correctness sudah baik. Selain itu, kode yang dibuat juga mudah di-maintain karena mengikuti prinsip SOLID. Meskipun terasa lama, flow TDD tersebut juga produktif karena kita bisa memastikan bahwa kode yang dibuat harus berhasil melalui tes yang kita buat.

2. Menurut saya, tes yang dibuat sudah memenuhi prinsip FIRST. Prinsip-prinsip yang dipenuhi:
- Fast: Testing bisa dilakukan dengan cepat (sekitar 10 detik untuk kedua file tersebut).
- Independent: Setiap tes yang ada independen dari tes lainnya. Contohnya adalah dengan menggunakan @Test yang independen satu sama lain dan @BeforeEach untuk inisialisasi ulang data (setup).
- Repeatable: Testing bisa diulang dan memberikan hasil yang sama juga untuk kondisi yang sama.
- Self-validating: Menggunakan assertions dari JUnit seperti assertEquals, assertNull, dan lainnya sesuai pada objek yang ingin dites. Jika ada kesalahan, kita bisa tau bagian mana yang salah berdasarkan assertion tersebut.
- Thorough and Timely: Menguji happy path seperti testCreateOrder() dengan create order yang valid. Pengujian juga dilakukan untuk unhappy path seperti testFindByIdIfNotFound() yang melakukan pencarian berdasarkan ID untuk ID yang tidak ada pada repository.