var check = function () {

    if (document.getElementById('password').value ==
        document.getElementById('confirm_password').value
        &&document.getElementById('password').value.length!==0&&document.getElementById('password').value.length){
        document.getElementById('message').style.color = 'green';
        document.getElementById('message').innerHTML = 'Пароли совпадают';
        document.getElementById('registerButton').disabled = false;
        document.getElementById('confirm_password').style.borderColor = 'green'
    } else {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerHTML = 'Passwords do not match';
        document.getElementById('registerButton').disabled = true;
        document.getElementById('confirm_password').style.borderColor = 'red';
    }
}
