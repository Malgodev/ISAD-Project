function showSection(sectionId) {

    console.log("dsfkm");
    // Hide all sections
    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
    });
    // Show the selected section
    document.getElementById(sectionId).classList.add('active');

    // Change the URL without reloading the page
    const sectionName = sectionId.replace('Section', '').toLowerCase(); // e.g., "login", "register", etc.
    history.pushState(null, '', `/${sectionName}`);
}

function validatePassword(fieldName) {
    const newPassword = document.getElementById(fieldName).value;
    const errorMessage = document.getElementById('passwordError');
    const passwordPattern = /^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$&*])[A-Za-z\d@$!%*?&]{8,}$/;

    if (!passwordPattern.test(newPassword)) {
        errorMessage.textContent = 'Password must be at least 8 characters long, contain at least one numeric digit, one uppercase letter, and one special character (e.g., #, @, $, &).';
        return false;
    } else {
        errorMessage.textContent = ''; // Clear error message if validation passes
        return true;
    }
}
