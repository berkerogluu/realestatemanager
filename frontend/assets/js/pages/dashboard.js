let customers = [];
let properties = [];

document.addEventListener("DOMContentLoaded", function() {
    loadDashboardData();
});

async function loadDashboardData() {
    try {
        await Promise.all([
            loadCustomers(),
            loadProperties()
        ]);
        updateDashboard();
    } catch (error) {
        console.error(error);
    }
}

async function loadCustomers() {
    try {
        const response = await fetch(`${BASE_URL}/api/customers`);
        if (response.ok) {
            customers = await response.json();
        }
    } catch (error) {
        console.error(error);
    }
}

async function loadProperties() {
    try {
        const response = await fetch(`${BASE_URL}/api/properties`);
        if (response.ok) {
            properties = await response.json();
        }
    } catch (error) {
        console.error(error);
    }
}

function updateDashboard() {
    const countElements = document.querySelectorAll('.text-dark.fw-bold.h5.mb-0 span');

    countElements[0].textContent = customers.length;
    countElements[1].textContent = properties.length;
}
