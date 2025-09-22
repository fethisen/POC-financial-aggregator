# POC Financial Aggregator

## Proje Amacı

Bu uygulama, kullanıcıların farklı finansal kaynaklardan (banka hesapları, krediler, yatırımlar, kredi kartları ve tasarruflar gibi) verilerini eşzamanlı olarak toplayarak bir araya getirir ve finansal durumlarına dair bütünsel bir özet sunar. Amaç, dağınık finansal verileri merkezi bir noktada toplayıp, kullanıcının finansal sağlığını tek bakışta görebileceği bir özet oluşturmaktır.

---

## Genel Mimari

Proje iki ana paketten oluşur:

- **summary-service**: Tüm finansal verileri toplayan ve özetleyen ana servis.
- **service-providers**: Her biri farklı bir finansal kaynağı (hesap, kredi, yatırım, kredi kartı, tasarruf) temsil eden mikroservisler.

Her bir mikroservis bağımsız çalışır ve kendi verisini REST API üzerinden sunar. summary-service ise bu mikroservislerden verileri eşzamanlı olarak çekip birleştirir.

