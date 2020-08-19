let date = "";
let previousHourId = null;
let selectHour = "";
let selectedServices = new Set();

function getHour(event) {
    event.preventDefault();
    if(previousHourId != null) {
        document.getElementById(previousHourId).classList.remove("bg-blue-500");
        document.getElementById(previousHourId).classList.remove("text-white");
        document.getElementById(previousHourId).classList.add("text-blue-700");
        previousHourId = null;
    }
    
    if (event.target.classList.contains("bg-blue-500")) {
        event.target.classList.remove("bg-blue-500");
        event.target.classList.remove("text-white");
        event.target.classList.add("text-blue-700");
    } else {
        event.target.classList.remove("text-blue-700");
        event.target.classList.add("bg-blue-500", "text-white");
    }

    selectHour = event.target.innerText;
    previousHourId = event.target.id;

}

function getService(event) {
    event.preventDefault();
    if(selectedServices.has(event.target.id)){
        event.target.classList.remove("bg-gray-400");
        event.target.classList.add("bg-gray-300");
        selectedServices.delete(event.target.id);
    } else {
        selectedServices.add(event.target.id);
        event.target.classList.remove("bg-gray-300");
        event.target.classList.add("bg-gray-400");  
    }
    console.log(Array.from(selectedServices));
    console.log(JSON.stringify(selectedServices));

}

function getDateValue(event) {
    event.preventDefault();
    if (event.target.classList.contains("bg-blue-500")) {
        event.target.classList.remove("bg-blue-500");
        event.target.classList.remove("text-white");
    } else {
        event.target.classList.add("bg-blue-500", "text-white");
    }

    let today = new Date();
    let selectedDate = new Date(
        today.getFullYear(),
        today.getMonth()+1,
        parseInt(event.currentTarget.innerText)
    );
    this.datepickerValue = selectedDate.toDateString();

    date =
        selectedDate.getFullYear() +
        "-" +
        ("0" + selectedDate.getMonth()).slice(-2) +
        "-" +
        ("0" + selectedDate.getDate()).slice(-2);
}


function getStepTwo() {
    document.getElementById("selectedDate").value = date;
    sessionStorage.setItem('selectedDate', date);
}

function getStepThree() {
    
    sessionStorage.setItem('selectedHour', selectHour);
    sessionStorage.setItem('selectedService', Array.from(selectedServices));

    document.getElementById("selectedHour").value = selectHour;
    document.getElementById("selectedService").value = Array.from(selectedServices);
}

function validateLastStep() {
    if(document.getElementById("user-phone").value.length === 0 &&
    document.getElementById("user-email").value.length === 0) {
        document.getElementById("warning-msg").classList.add("text-red-600");
        event.preventDefault();
    } else {
        document.getElementById("selectedHour").value = sessionStorage.getItem('selectedHour');
        document.getElementById("selectedService").value =  sessionStorage.getItem('selectedService');
        document.getElementById("selectedDate").value = sessionStorage.getItem('selectedDate');
    }

}
