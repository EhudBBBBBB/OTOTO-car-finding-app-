package com.example.ototocarsrentingapp.auth.Validator;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Validator {
    //"^" אומר שהבדיקה מתחילה מהתו הראשון
    //"[]" קבוצת תווים שיכולה להפויעA-Z
    //"$" סוף המחרוזת
    //"*" צריך להופיע 0 או יותר פעמים
    //"+" צריך להופיע פעם אחת לפחות
    //"{n}" צריך להופיע בדיוק n פעמים
    //"{n,m}" צריך להופיע בין N לM פעמים
    //"?=" חייב להכיל
    //"?!" אסור להכיל

    //regex לכל תכונה
    //============================================================

    //שם פרטי
    private static final String NAME_REGEX = "^[א-ת]{2,20}$";

    //שם משפחה
    private static final String FAMILY_NAME_REGEX = "^[א-ת]{2,20}$";

    // אימייל: חייב @, נקודה בדומיין, ללא רווחים
    private static final String EMAIL_REGEX =  "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$";

    //מספר טלפון
    private static final String PHONE_REGEX = "^05[0-9]{8}$";

    //תאריך לידה
    private static final String BIRTHDATE_REGEX = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$";

    // כתובת: אותיות, מספרים, רווחים, נקודה, פסיק, מינוס; 5-100 תווים
    private static final String ADDRESS_REGEX = "^[A-Za-z0-9א-ת\\s\\.,-]{5,100}$";

    // עיר: אותיות עבריות ורווחים; 2-50 תווים
    private static final String CITY_REGEX = "^[א-ת\\s]{2,50}$";

    // מיקוד: 5 ספרות
    private static final String POSTCODE_REGEX = "^[0-9]{5}$";

    // סיסמה חזקה: לפחות 8 תווים, אות גדולה, ספרה, תו מיוחד, ללא רווחים
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";

    //============================================================


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

        if (!firstName.matches(NAME_REGEX)) {
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

        if (!lastName.matches(FAMILY_NAME_REGEX)) {
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
        if (!birthDate.matches(BIRTHDATE_REGEX)) {
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
        if (!email.matches(EMAIL_REGEX)) return new ValidationResult("אימייל לא תקין: יש להזין כתובת בפורמט name@example.com", false);

        return new ValidationResult(null, true);
    }

    //מתודה שבודקת האם המספר טלפון תקין
    public static ValidationResult validatePhone(String phone) {
        if (phone == null) return new ValidationResult("נא להזין מספר טלפון", false);

        phone = phone.trim();
        if (phone.isEmpty()) return new ValidationResult("נא להזין מספר טלפון", false);

        phone = phone.replaceAll("[\\s-]", "");
        if (!phone.matches(PHONE_REGEX))  return new ValidationResult("מספר טלפון לא תקין: יש להזין מספר נייד ישראלי בן 10 ספרות שמתחיל ב-05 ", false);

        return new ValidationResult(null, true);
    }

    //מתודה שבודקת האם הכתובת תקינה
    public static ValidationResult validateAddress(String address) {
        if (address == null) return new ValidationResult("נא להזין כתובת", false);

        address = address.trim();
        if (address.isEmpty())  return new ValidationResult("כתובת לא תקינה: יש להזין 5–100 ", false);

        // מנרמל רווחים מרובים לרווח אחד
        address = address.replaceAll("\\s+", " ");

        if (!address.matches(ADDRESS_REGEX)) return new ValidationResult("כתובת לא תקינה", false);

        return new ValidationResult(null, true);
    }

    //מתודה שבודקת האם העיר תקינה
    public static ValidationResult validateCity(String city) {
        if (city == null) return new ValidationResult("נא להזין עיר", false);

        city = city.trim();
        if (city.isEmpty()) return new ValidationResult("נא להזין עיר", false);

        city = city.replaceAll("\\s+", " ");
        if (!city.matches(CITY_REGEX)) return new ValidationResult("שם העיר לא תקין: יש להזין 2–50 תווים בעברית בלבד (מותר רווחים)", false);

        return new ValidationResult(null, true);
    }

    //מתודה שבודקת האם הסיסמה תקינה
    public static ValidationResult validatePassword(String password) {
        if (password == null) return new ValidationResult("נא להזין סיסמה", false);

        password = password.trim();
        if (password.isEmpty()) return new ValidationResult("נא להזין סיסמה", false);

        if (!password.matches(PASSWORD_REGEX)) {
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
}
