# Staj Yönetim Sistemi Fakülte Sorumlusu Arayüz Modülü

Staj yönetim sistemi uygulaması, fakülte sorumlusu arayüz modülüdür. React framework'ü kullanılmıştır.

## Çalıştırmadan Önce

-  _env.development.local_ dosyasını çalışma ortamına göre düzenlenmelidir.
-  **npm install** komutu kullanılarak proje bağımlıklıkları yüklenmelidir.

## Kullanılabilir Komutlar

### `npm install`

Proje bağımlılıklarını yükler.

### `npm start`

Projeyi [http://localhost:3000](http://localhost:3000) adresinde ayağa kaldırır.

### `npm test`

Arayüz testlerini çalıştırır.

### `npm build`

Projeyi derleyip, **_/build_** dizinine derlenmiş dosyaları oluşturur.

## Dosya Yapısı

### `components`

Proje içerisinde kullanılan, özelleştirilmiş react componentleri bulunur.

### `containers`

Proje ekranları bulunur.

### `hoc`

React High-Order Component'lerinin bulunur.

### `shared`

Proje içerisinde birden fazla yerde ortak olarak kullanılan metodlar bulunur.
