
# 📱 AntiScamApp

แอป Android สำหรับช่วยป้องกันและแจ้งเตือนผู้ใช้เมื่อมีเบอร์โทรต้องสงสัยว่าเป็น **สแกมเมอร์**  
ทำงานโดยการ:
- Sync เบอร์สแกมจาก **Firebase Realtime Database** ลง SQLite (Room)
- ตรวจสอบเบอร์ที่โทรเข้า ด้วย `CallScreeningService`
- ถ้าเป็นเบอร์ต้องสงสัย → พูดเสียงเตือน:  
  **"โปรดระวัง เบอร์นี้ต้องสงสัยว่าเป็นสแกมเมอร์"**

---

## 🚀 การติดตั้งและใช้งาน

### 1. Clone หรือแตกไฟล์ ZIP
แตกไฟล์ `AntiScamApp.zip` แล้วเปิดใน **Android Studio**

### 2. ตั้งค่า Firebase
1. ไปที่ [Firebase Console](https://console.firebase.google.com/)  
2. สร้าง Project ใหม่ → เพิ่ม Android App (`com.example.antiscam`)  
3. ดาวน์โหลดไฟล์ `google-services.json`  
4. วางแทนที่ไฟล์ `app/google-services.json` (ไฟล์ dummy ที่ให้มา)

### 3. เพิ่มเบอร์สแกมใน Firebase
ใน Realtime Database เพิ่ม node เช่น:
```json
{
  "scam_numbers": {
    "1": "0812345678",
    "2": "0998765432"
  }
}
```

### 4. ติดตั้งบนมือถือ (Android 10 ขึ้นไป)
- ต่อมือถือ → Run App จาก Android Studio  
- เปิดแอป → กดปุ่ม **"ตั้งเป็นแอปป้องกันสแกม"**  
  → เลือก AntiScamApp เป็น **Default Call Screening App**  

### 5. การทำงาน
- แอปจะ Sync เบอร์สแกมจาก Firebase  
- ถ้ามีเบอร์โทรเข้าที่อยู่ใน blacklist → จะมีเสียงแจ้งเตือนอัตโนมัติ  

---

## ⚠️ หมายเหตุ
- แอปนี้ต้องการ **Android 10 (API 29)** ขึ้นไป  
- ถ้า TTS (Text-to-Speech) ไม่พูด ให้ตรวจสอบว่าเครื่องติดตั้ง **Google TTS Engine** แล้วหรือไม่  
- ผู้ใช้สามารถเพิ่มเบอร์ blacklist เองได้ในหน้า **MainActivity**  

---

## 📂 โครงสร้างโปรเจกต์
```
AntiScamApp/
 ├─ app/
 │   ├─ src/main/java/com/example/antiscam/
 │   │   ├─ MainActivity.kt
 │   │   ├─ MyCallScreeningService.kt
 │   │   └─ data/
 │   │       ├─ ScamNumber.kt
 │   │       ├─ ScamDao.kt
 │   │       ├─ AppDatabase.kt
 │   │       └─ FirebaseSync.kt
 │   ├─ src/main/AndroidManifest.xml
 │   ├─ build.gradle
 │   └─ google-services.json (ต้องแทนที่ด้วยของจริง)
 ├─ build.gradle
 └─ settings.gradle
```

---

## 👨‍💻 ผู้พัฒนา
ตัวอย่างโค้ดโดย **ChatGPT + ผู้ใช้** ✨
