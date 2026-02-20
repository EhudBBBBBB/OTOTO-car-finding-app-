package com.example.ototocarsrentingapp.auth.Validator;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.ototocarsrentingapp.model.CarManufacturer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {
    // Map שמכיל את כל הדגמים לפי יצרן
    private static final Map<CarManufacturer, List<String>> validModelsByMake = new HashMap<>();

    //"^" אומר שהבדיקה מתחילה מהתו הראשון
    //"[]" קבוצת תווים שיכולה להפויעA-Z
    //"$" סוף המחרוזת
    //"*" צריך להופיע 0 או יותר פעמים
    //"+" צריך להופיע פעם אחת לפחות
    //"{n}" צריך להופיע בדיוק n פעמים
    //"{n,m}" צריך להופיע בין N לM פעמים
    //"?=" חייב להכיל
    //"?!" אסור להכיל

    //regex לכל תכונה user
    //============================================================

    //שם פרטי
    private static final String nameRegex = "^[א-ת]{2,20}$";

    //שם משפחה
    private static final String familyNameRegex = "^[א-ת]{2,20}$";

    // אימייל: חייב @, נקודה בדומיין, ללא רווחים
    private static final String emailRegex =  "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$";

    //מספר טלפון
    private static final String phoneNumRegex = "^05[0-9]{8}$";

    //תאריך לידה
    private static final String birthDateRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$";

    // כתובת: אותיות, מספרים, רווחים, נקודה, פסיק, מינוס; 5-100 תווים
    private static final String addressRegex = "^[A-Za-z0-9א-ת\\s\\.,-]{5,100}$";

    // עיר: אותיות עבריות ורווחים; 2-50 תווים
    private static final String cityRegex = "^[א-ת\\s]{2,50}$";

    // מיקוד: 5 ספרות

    // סיסמה חזקה: לפחות 8 תווים, אות גדולה, ספרה, תו מיוחד, ללא רווחים
    private static final String passwordRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";

    //============================================================
    //regex לכל תכונה renter
    private static final String driverLicense = "^\\d{7,8}$";//מספר רישיון רכב של הנהג

    //============================================================
    //regex לכל תכונה Seller

    // מספר רישוי ישראלי: 7–8 ספרות בלבד
    public static final String LICENSE_PLATE_REGEX = "^\\d{7,9}$";//לוחית רישוי

    // קילומטרים: מספר שלם חיובי (לא מתחיל ב־0)
    public static final String KILOMETERS_REGEX = "^[1-9]\\d*(?:[.,]\\d+)*$";
    // מספר מושבים: ספרה אחת בין 1 ל־9
    public static final String SEATS_NUMBER_REGEX = "^[1-7]$";

    // שנת רכב: טווח סביר של שנים (1950–2029)
    public static final String YEAR_REGEX = "^(19[5-9]\\d|20[0-2]\\d)$";


    //מתודות ולידציה לכל תכונה
    //============================================================

    //מתודה שבודקת אם השם תקין
    public static ValidationResult validateFirstName(String firstName) {
        if (firstName == null) {
            return new ValidationResult("נא להזין שם פרטי", false);
        }

        firstName = firstName.trim();
        if (firstName.isEmpty()) {
            return new ValidationResult("נא להזין שם פרטי", false);
        }

        if (!firstName.matches(nameRegex)) {
            return new ValidationResult("שם פרטי לא תקין (2–20 אותיות בעברית)", false);
        }

        return new ValidationResult(null, true);
    }
    //מתודה שבודקת אם שם המשפחה תקין
    public static ValidationResult validateLastName(String lastName) {
        if (lastName == null) {
            return new ValidationResult("נא להזין שם משפחה", false);
        }

        lastName = lastName.trim();
        if (lastName.isEmpty()) {
            return new ValidationResult("נא להזין שם משפחה", false);
        }

        if (!lastName.matches(familyNameRegex)) {
            return new ValidationResult("שם משפחה לא תקין (2–20 אותיות בעברית)", false);
        }

        return new ValidationResult(null, true);
    }

    //מתודה שבודקת האםם התאריך לידה תקין
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ValidationResult validateBirthDate(String birthDate) {
        if (birthDate == null) {
            return new ValidationResult("נא להזין תאריך לידה", false);
        }
        birthDate = birthDate.trim();
        if (birthDate.isEmpty()) {
            return new ValidationResult("נא להזין תאריך לידה", false);
        }
        if (!birthDate.matches(birthDateRegex)) {
            return new ValidationResult("תאריך לידה לא תקין: יש להזין בפורמט DD/MM/YYYY (לדוגמה 05/11/2008)", false);
        }

        // 6) בדיקת "תאריך אמיתי" באמצעות LocalDate + STRICT
        //    STRICT אומר: אל תסלח על תאריכים לא קיימים כמו 31/02/2008
        try {
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

            LocalDate date = LocalDate.parse(birthDate, formatter);

            int year = date.getYear();
            int currentYear = LocalDate.now().getYear();

            if (year < 1900 || year > currentYear) {
                return new ValidationResult("שנת לידה לא סבירה", false);
            }

        } catch (DateTimeParseException e) {
            // אם parse נכשל -> זה לא תאריך אמיתי
            return new ValidationResult("תאריך לידה לא תקין", false);
        }

        // אם עבר הכל - תקין
        return new ValidationResult(null, true);
    }

    //מתודה שבודקת האם האימייל תקין
    public static ValidationResult validateEmail(String email) {
        if (email == null) return new ValidationResult("נא להזין אימייל", false);

        email = email.trim();
        if (email.isEmpty()) return new ValidationResult("נא להזין אימייל", false);
        if (email.contains(" ")) return new ValidationResult("אימייל לא יכול להכיל רווחים", false);
        if (!email.matches(emailRegex)) return new ValidationResult("אימייל לא תקין: יש להזין כתובת בפורמט name@example.com", false);

        return new ValidationResult(null, true);
    }

    //מתודה שבודקת האם המספר טלפון תקין
    public static ValidationResult validatePhone(String phone) {
        if (phone == null) return new ValidationResult("נא להזין מספר טלפון", false);

        phone = phone.trim();
        if (phone.isEmpty()) return new ValidationResult("נא להזין מספר טלפון", false);

        phone = phone.replaceAll("[\\s-]", "");
        if (!phone.matches(phoneNumRegex))  return new ValidationResult("מספר טלפון לא תקין: יש להזין מספר נייד ישראלי בן 10 ספרות שמתחיל ב-05 ", false);

        return new ValidationResult(null, true);
    }

    //מתודה שבודקת האם הכתובת תקינה
    public static ValidationResult validateAddress(String address) {
        if (address == null) return new ValidationResult("נא להזין כתובת", false);

        address = address.trim();
        if (address.isEmpty())  return new ValidationResult("כתובת לא תקינה: יש להזין 5–100 ", false);

        // מנרמל רווחים מרובים לרווח אחד
        address = address.replaceAll("\\s+", " ");

        if (!address.matches(addressRegex)) return new ValidationResult("כתובת לא תקינה", false);

        return new ValidationResult(null, true);
    }

    //מתודה שבודקת האם העיר תקינה
    public static ValidationResult validateCity(String city) {
        if (city == null) return new ValidationResult("נא להזין עיר", false);

        city = city.trim();
        if (city.isEmpty()) return new ValidationResult("נא להזין עיר", false);

        city = city.replaceAll("\\s+", " ");
        if (!city.matches(cityRegex)) return new ValidationResult("שם העיר לא תקין: יש להזין 2–50 תווים בעברית בלבד (מותר רווחים)", false);

        return new ValidationResult(null, true);
    }

    //מתודה שבודקת האם הסיסמה תקינה
    public static ValidationResult validatePassword(String password) {
        if (password == null) return new ValidationResult("נא להזין סיסמה", false);

        password = password.trim();
        if (password.isEmpty()) return new ValidationResult("נא להזין סיסמה", false);

        if (!password.matches(passwordRegex)) {
            return new ValidationResult("סיסמה לא תקינה: לפחות 8 תווים, אות גדולה, ספרה, תו מיוחד, ללא רווחים", false);
        }

        return new ValidationResult(null, true);
    }

    //מתודה שבודקת האם הסיסמה והאישור סיסמה זהות
    public static ValidationResult validateConfirmPassword(String password, String confirmPassword) {
        if (password == null || password.trim().isEmpty()) {
            return new ValidationResult("נא להזין סיסמה לפני אימות", false);
        }

        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            return new ValidationResult("נא לאשר סיסמה", false);
        }

        password = password.trim();
        confirmPassword = confirmPassword.trim();

        if (!confirmPassword.equals(password)) {
            return new ValidationResult("הסיסמה ואישור הסיסמה אינם תואמים", false);
        }

        return new ValidationResult(null, true);
    }
    //מתודות בדיקה עבור שדות של renter
    public static ValidationResult validateLicenseNumber(String licenseNumber) {
        if (licenseNumber == null) {
            return new ValidationResult("נא להזין מספר רישיון", false);
        }

        licenseNumber = licenseNumber.trim();

        if (licenseNumber.isEmpty()) {
            return new ValidationResult("נא להזין מספר רישיון", false);
        }
        if (!licenseNumber.matches(driverLicense)){
            return new ValidationResult("נא להזין מספר רישיון נהיגה תקין (7–8 ספרות בלבד)", false);
        }
        return new ValidationResult(null, true);
    }

    //-----------------------------------------------------------
    //מתודות בדיקה עבור כל השדות של SELLER
    //שלוחית רישוי תקינה
    public static ValidationResult  validateLicensePlate(String licensePlate) {
        if(licensePlate == null) return new ValidationResult("נא להזין מספר רישיון", false);

        licensePlate = licensePlate.trim();
        if(licensePlate.isEmpty()) return new ValidationResult("נא להזין מספר רישיון", false);
        if(!licensePlate.matches(LICENSE_PLATE_REGEX)) return new ValidationResult("נא להזין מספר לוחית רישיו  תקינה (7–9 ספרות בלבד)", false);

        return new ValidationResult(null, true);
    }
    //בדיקה שמספר הקילומטרים תקין
    public static ValidationResult validateKilometers(String kilometers) {
        if(kilometers == null) {
            return new ValidationResult("נא להזין מספר קילומטרים", false);

        }

        kilometers = kilometers.trim();

        if(kilometers.isEmpty()) {
            return new ValidationResult("נא להזין מספר קילומטרים", false);

        }
        if(!kilometers.matches(KILOMETERS_REGEX)) {
            return new ValidationResult("נא  להזין מספר קילומטרים תקין (מספר חיובי בלבד)", false);

        }
        return new ValidationResult(null, true);
    }
    //בדיקה שמספר השנים תקין
    public static ValidationResult validateYears(String years) {
        if (years == null) {
            return new ValidationResult("נא להזין מספר שנים", false);
        }

        years = years.trim();

        if (years.isEmpty()) {
            return new ValidationResult("נא להזין מספר שנים", false);
        }
        if (!years.matches(YEAR_REGEX)) {
            return new ValidationResult("נא להזין מספר שנים תקין (בין 1950-2026)", false);
        }
        return new ValidationResult(null, true);
    }
    //בדיקה שמספר הכיסאות תקין
    public static ValidationResult validateSeatsNumbers(String capacity) {
        if (capacity == null) {
            return new ValidationResult("נא להזין מספר כיסאות", false);
        }

        capacity = capacity.trim();

        if (capacity.isEmpty()) {
            return new ValidationResult("נא להזין מספר כיסאות", false);
        }
        if (!capacity.matches(SEATS_NUMBER_REGEX)) {
            return new ValidationResult("נא להזין מספר כיסאות תקין (ספרה חיובית בלבד)", false);
        }
        return new ValidationResult(null, true);
    }

    //-----------------------------------------------------------------------------
    //טעינת ערכים קבועים לתוך מבנה הנתונים MAP
    // static block – נטען פעם אחת בלבד

    static {

        validModelsByMake.put(CarManufacturer.FIAT, Arrays.asList(
                "500",
                "Panda",
                "Punto",
                "Tipo",
                "Doblo"
        ));

        validModelsByMake.put(CarManufacturer.SUZUKI, Arrays.asList(
                "Swift",
                "Baleno",
                "Vitara",
                "SX4",
                "Jimny"
        ));

        validModelsByMake.put(CarManufacturer.TOYOTA, Arrays.asList(
                "Corolla",
                "Yaris",
                "Camry",
                "RAV4",
                "Prius",
                "C-HR"
        ));

        validModelsByMake.put(CarManufacturer.HYUNDAI, Arrays.asList(
                "i10",
                "i20",
                "i30",
                "Elantra",
                "Tucson",
                "Kona"
        ));

        validModelsByMake.put(CarManufacturer.KIA, Arrays.asList(
                "Picanto",
                "Rio",
                "Ceed",
                "Sportage",
                "Sorento"
        ));

        validModelsByMake.put(CarManufacturer.SKODA, Arrays.asList(
                "Fabia",
                "Octavia",
                "Superb",
                "Kodiaq"
        ));

        validModelsByMake.put(CarManufacturer.VOLKSWAGEN, Arrays.asList(
                "Polo",
                "Golf",
                "Passat",
                "Tiguan"
        ));

        validModelsByMake.put(CarManufacturer.MAZDA, Arrays.asList(
                "2",
                "3",
                "6",
                "CX-3",
                "CX-5"
        ));

        validModelsByMake.put(CarManufacturer.HONDA, Arrays.asList(
                "Civic",
                "Accord",
                "Jazz",
                "CR-V"
        ));

        validModelsByMake.put(CarManufacturer.FORD, Arrays.asList(
                "Fiesta",
                "Focus",
                "Mondeo",
                "Escape"
        ));

        validModelsByMake.put(CarManufacturer.CHEVROLET, Arrays.asList(
                "Spark",
                "Cruze",
                "Malibu"
        ));

        validModelsByMake.put(CarManufacturer.BMW, Arrays.asList(
                "116i",
                "118i",
                "320i",
                "X1",
                "X3",
                "X5"
        ));

        validModelsByMake.put(CarManufacturer.MERCEDES, Arrays.asList(
                "A-Class",
                "C-Class",
                "E-Class",
                "GLA",
                "GLC"
        ));

        validModelsByMake.put(CarManufacturer.AUDI, Arrays.asList(
                "A1",
                "A3",
                "A4",
                "A6",
                "Q3",
                "Q5"
        ));

        validModelsByMake.put(CarManufacturer.TESLA, Arrays.asList(
                "Model 3",
                "Model S",
                "Model X",
                "Model Y"
        ));

        validModelsByMake.put(CarManufacturer.BYD, Arrays.asList(
                "Atto 3",
                "Seal",
                "Dolphin"
        ));

        validModelsByMake.put(CarManufacturer.NISSAN, Arrays.asList(
                "Micra",
                "Sentra",
                "Altima",
                "Qashqai",
                "X-Trail"
        ));

        validModelsByMake.put(CarManufacturer.MITSUBISHI, Arrays.asList(
                "Space Star",
                "ASX",
                "Outlander"
        ));

        validModelsByMake.put(CarManufacturer.PEUGEOT, Arrays.asList(
                "108",
                "208",
                "308",
                "3008",
                "5008"
        ));
    }
    //-----------------------------------------------------------------------------------
    //פונקציה סטטית שבודקת האם היצרן של הרכב והמודל של הרכב תקינים
    public static ValidationResult validateModel(CarManufacturer carType, String userModel) {

        // בדיקת null
        if (carType == null) {
            return new ValidationResult("יש לבחור יצרן רכב", false);
        }

        if (userModel == null || userModel.trim().isEmpty()) {
            return new ValidationResult("יש להזין דגם רכב", false);
        }

        List<String> validModels = validModelsByMake.get(carType);

        if (validModels == null) {
            return new ValidationResult("לא נמצאו דגמים ליצרן שנבחר", false);
        }

        for (String model : validModels) {
            if (model.equalsIgnoreCase(userModel.trim())) {
                return new ValidationResult(null, true);
            }
        }

        return new ValidationResult("הדגם אינו שייך ליצרן שנבחר", false);
    }
}
