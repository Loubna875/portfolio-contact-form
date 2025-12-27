// DOM Selection
const firstName = document.getElementById("firstName");
const lastName = document.getElementById("lastName");
const email = document.getElementById("email");
const phone = document.getElementById("phone");
const subject = document.getElementById("subject");
const message = document.getElementById("message");
const charCounter = document.querySelector(".char-counter");
const successMessage = document.getElementById("successMessage");
const form = document.getElementById("contactForm");


// Validation functions will be added here

function validateFirstName () {
 const value = firstName.value.trim();
 const regex = /^[A-Za-z]+$/;
 if (!regex.test(value)) {
    showError(firstName, "First name must cintain only letters");
    return false;
    }
    clearError(firstName);
    return true;
    }

function validateLastName () {
 const value = firstName.value.trim();
 const regex = /^[A-Za-z]+$/;
 if (!regex.test(value)) {
    showError(lastName, "Last name must cintain only letters");
    return false;
    }
    clearError(lastName);
    return true;
    }
    
function validateEmail () {
    const value = email.value.trim();
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!regex.test(value)) {
        showError(email, "Please enter a valid email address");
        return false;
        }
        
        clearError(email);
        return true;
        }
function validateMessage () {}

function showError () {}
function clearError () {}

function clearForm () {}

