# POC Financial Aggregator

## Proje Amacı

Bu uygulama, kullanıcıların farklı finansal kaynaklardan (banka hesapları, krediler, yatırımlar, kredi kartları ve tasarruflar gibi) verilerini eşzamanlı olarak toplayarak bir araya getirir ve finansal durumlarına dair bütünsel bir özet sunar. Amaç, dağınık finansal verileri merkezi bir noktada toplayıp, kullanıcının finansal sağlığını tek bakışta görebileceği bir özet oluşturmaktır.

---

## Genel Mimari

Proje iki ana paketten oluşur:

- **summary-service**: Tüm finansal verileri toplayan ve özetleyen ana servis.
- **service-providers**: Her biri farklı bir finansal kaynağı (hesap, kredi, yatırım, kredi kartı, tasarruf) temsil eden mikroservisler.

Her bir mikroservis bağımsız çalışır ve kendi verisini REST API üzerinden sunar. summary-service ise bu mikroservislerden verileri eşzamanlı olarak çekip birleştirir.

---

## Paketler ve Servisler

### 1. summary-service

#### Amaç
Kullanıcıya ait tüm finansal verileri farklı mikroservislerden asenkron olarak toplayıp, tek bir özet veri halinde sunar.

#### Teknik Detaylar
- **Multi-thread ve Asenkron Yapı**: 
  - Veriler, her bir finansal servis için ayrı thread'ler üzerinden çekilir.
  - Spring'in `@Async` anotasyonu ve `ThreadPoolTaskExecutor` ile thread havuzu yönetimi sağlanır.
  - Her bir servis çağrısı için thread başına ve HTTP isteği başına ayrı zaman aşımı (timeout) tanımlanabilir.
    - Örneğin, `AsyncWorkerService` içinde:
      - WebClient ile yapılan HTTP çağrısı için 3 saniyelik bir bekleme süresi (`WAIT_TIME_FOR_WEBCLIENT`)
      - Thread'in toplam çalışma süresi için 4 saniyelik bir üst sınır (`WAIT_TIME_FOR_THREAD`)
    - Bu sayede, bir mikroservis yanıt vermezse veya takılırsa, thread belirlenen sürede otomatik olarak sonlandırılır ve sistemin genel performansı etkilenmez.
- **Kullanılan Teknolojiler**:
  - Spring Boot
  - Spring WebFlux (asenkron HTTP çağrıları için)
  - CompletableFuture (eşzamanlı veri toplama)
  - ThreadPoolTaskExecutor (thread havuzu yönetimi)
- **Özet Akış**:
  1. summary-service, her bir mikroservise asenkron HTTP isteği gönderir.
  2. Tüm yanıtlar toplandıktan sonra, veriler birleştirilir ve kullanıcının toplam finansal durumu hesaplanır.
  3. Sonuç, tek bir JSON response olarak döner.

#### Konfigürasyon
- Thread havuzu, `AsyncConfig` ile yönetilir:
  - Minimum 5, maksimum 10 thread.
  - 50 işlik kuyruk kapasitesi.
  - Thread isimleri `Worker-` ile başlar.
- Servis endpointleri `application.yml` dosyasında tanımlanır.

### 2. service-providers

#### Amaç
Her biri farklı bir finansal veri kaynağını temsil eden mikroservislerdir. Her servis kendi verisini REST API ile sunar.

#### Alt Servisler
- **account**: Banka hesap bilgilerini sunar.
- **loan**: Kredi bilgilerini sunar.
- **investment**: Yatırım bilgilerini sunar.
- **credit-card**: Kredi kartı bilgilerini sunar.
- **saving**: Tasarruf bilgilerini sunar.

Her bir servis:
- Kendi başına bir Spring Boot uygulamasıdır.
- `/api/{servis-adı}` endpoint'i ile ilgili verileri döner.
- Controller katmanında, örnek veri veya gerçek veri kaynağından alınan bilgiler sunulur.

---

## Multi-thread ve Zaman Aşımı (Timeout) Yönetimi

- Her bir finansal servis çağrısı ayrı bir thread'de çalışır.
- Her thread için ve HTTP isteği için ayrı ayrı bekleme süresi (timeout) tanımlanabilir.
- Örneğin, bir mikroservis 3 saniyede yanıt vermezse, thread toplamda 4 saniyede otomatik olarak sonlandırılır.
- Bu yapı, sistemin yavaşlayan veya yanıt vermeyen servislerden etkilenmeden hızlı ve güvenilir çalışmasını sağlar.

---

## Kullanılan Sistemler ve Teknolojiler

- **Spring Boot**: Tüm servislerin temel çatısı.
- **Spring WebFlux**: Asenkron ve reaktif HTTP çağrıları için.
- **CompletableFuture**: Eşzamanlı veri toplama ve işleme.
- **ThreadPoolTaskExecutor**: Thread havuzu yönetimi.
- **Maven**: Proje yönetimi ve bağımlılık yönetimi.
- **Java 17**: Modern Java özellikleriyle geliştirilmiştir.

---

## Hızlı Başlangıç

Her bir mikroservisi ve summary-service'i ayrı ayrı başlatabilirsiniz. Varsayılan portlar ve endpointler `application.yml` dosyalarında tanımlıdır.

---

## Katkı ve Geliştirme

Proje, mikroservis mimarisi ve asenkron programlama konularında örnek ve POC (Proof of Concept) olarak tasarlanmıştır. Katkıda bulunmak veya geliştirmek için her bir servisin kendi kodunu ve summary-service'in işleyişini inceleyebilirsiniz.
