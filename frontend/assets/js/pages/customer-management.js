// Customer Management JavaScript
let customers = [];
let apiKey = '';

// DOM Elements
const customerTableBody = document.querySelector("#dataTable tbody");
const searchInput = document.querySelector("input[type='search']");

// Modal elements
const addCustomerModal = document.getElementById("modal-2");
const customerDetailsModal = document.getElementById("modal-1");
const deleteCustomerModal = document.getElementById("modal-3");

const nameInput = document.getElementById("name");
const lastnameInput = document.getElementById("last-name");
const phoneInput = document.getElementById("phone-number");
const homeNumberInput = document.getElementById("home-number");
const emailInput = document.getElementById("email");
const addressInput = document.getElementById("address");
const apiKeyInput = document.getElementById("api-key");
const deleteApiKeyInput = document.querySelector("#modal-3 input[type='text']");

document.addEventListener("DOMContentLoaded", function() {
    loadCustomers();
    setupEventListeners();
});

// Setup event listeners
function setupEventListeners() {
    const saveButton = document.querySelector("#modal-2 .btn-primary:last-child");
    saveButton.addEventListener('click', handleAddCustomer);

    const deleteButton = document.querySelector("#modal-3 .btn-primary:last-child");
    deleteButton.addEventListener("click", handleDeleteCustomer);

    searchInput.addEventListener("input", handleSearch);
}

// Load all customers
async function loadCustomers() {
    try {
        const response = await fetch(`${BASE_URL}/api/customers`);
        if (response.ok) {
            customers = await response.json();
            renderCustomersTable();
        }
    } catch (error) {
        console.error(error);
    }
}

// Render customers table
function renderCustomersTable(customersToShow = customers) {
    customerTableBody.innerHTML = '';

    customersToShow.forEach(customer => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${customer.id}</td>
            <td>${customer.customerName} ${customer.customerLastname}</td>
            <td>${customer.mobilePhone}</td>
            <td>${customer.email}</td>
            <td>
                <button class="btn btn-danger border rounded-0" type="button" onclick="showDeleteModal(${customer.id})">
                    <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-x">
                        <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"></path>
                    </svg>Delete
                </button>
                <button class="btn btn-primary border rounded-0" type="button" onclick="showCustomerDetails(${customer.id})" style="margin-left: 0;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-info">
                        <path d="m8.93 6.588-2.29.287-.082.38.45.083c.294.07.352.176.288.469l-.738 3.468c-.194.897.105 1.319.808 1.319.545 0 1.178-.252 1.465-.598l.088-.416c-.2.176-.492.246-.686.246-.275 0-.375-.193-.304-.533zM9 4.5a1 1 0 1 1-2 0 1 1 0 0 1 2 0"></path>
                    </svg>Details
                </button>
            </td>
        `;
        customerTableBody.appendChild(row);
    });
}

// Handle add customer
async function handleAddCustomer() {
    const customerData = {
        customerName: nameInput.value,
        customerLastname: lastnameInput.value,
        mobilePhone: phoneInput.value,
        homeNumber: homeNumberInput.value,
        email: emailInput.value,
        address: addressInput.value
    };

    apiKey = apiKeyInput.value;

    try {
        const response = await fetch(`${BASE_URL}/api/customers?apiKey=${apiKey}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(customerData)
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(addCustomerModal);
            modal.hide();
            clearAddCustomerForm();
            loadCustomers();
        } else {
            alert("Failed to create customer. Check your API key.");
        }
    } catch (error) {
        alert("Error creating customer");
    }
}

function showCustomerDetails(customerId) {
    const customer = customers.find(c => c.id === customerId);
    if (!customer) return;

    const detailsTable = document.querySelector("#modal-1 .table-striped-columns tbody");
    detailsTable.innerHTML = `
        <tr><td>Name</td><td>${customer.customerName}</td></tr>
        <tr><td>Lastname</td><td>${customer.customerLastname}</td></tr>
        <tr><td>Mobile Phone</td><td>${customer.mobilePhone}</td></tr>
        <tr><td>Home Number</td><td>${customer.homeNumber}</td></tr>
        <tr><td>Email</td><td>${customer.email}</td></tr>
        <tr><td>Address</td><td>${customer.address}</td></tr>
    `;

    const modal = new bootstrap.Modal(customerDetailsModal);
    modal.show();
}

let customerToDelete = null;
function showDeleteModal(customerId) {
    customerToDelete = customerId;
    const modal = new bootstrap.Modal(deleteCustomerModal);
    modal.show();
}

async function handleDeleteCustomer() {
    if (!customerToDelete) return;

    apiKey = deleteApiKeyInput.value.trim();
    if (!apiKey) {
        alert("Please provide API key");
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/api/customers/${customerToDelete}?apiKey=${apiKey}`, {
            method: "DELETE"
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(deleteCustomerModal);
            modal.hide();
            deleteApiKeyInput.value = "";
            customerToDelete = null;
            loadCustomers();
        } else {
            alert("Failed to delete customer. Check your API key.");
        }
    } catch (error) {
        alert("Error deleting customer");
    }
}

function handleSearch() {
    const searchTerm = searchInput.value.toLowerCase().trim();

    if (!searchTerm) {
        renderCustomersTable(customers);
        return;
    }

    const filteredCustomers = customers.filter(customer =>
        customer.customerName.toLowerCase().includes(searchTerm) ||
        customer.customerLastname.toLowerCase().includes(searchTerm) ||
        customer.email.toLowerCase().includes(searchTerm) ||
        customer.mobilePhone.includes(searchTerm)
    );

    renderCustomersTable(filteredCustomers);
}

// Clear add customer form
function clearAddCustomerForm() {
    nameInput.value = "";
    lastnameInput.value = "";
    phoneInput.value = "";
    homeNumberInput.value = "";
    emailInput.value = "";
    addressInput.value = "";
    apiKeyInput.value = "";
}
