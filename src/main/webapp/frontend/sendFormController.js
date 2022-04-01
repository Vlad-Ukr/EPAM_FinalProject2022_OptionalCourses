var isCommitted = false; // Если форма отправила логотип, по умолчанию используется значение false
function doSubmit() {
    if (isCommitted == false) {
        isCommitted = true; // После отправки формы укажите, была ли форма отправлена, чтобы установить значение true
       return  true; // Возвращаем true, чтобы форма отправлялась нормально
    } else {
        return false; // вернем false тогда форма не будет отправлена
    }
}