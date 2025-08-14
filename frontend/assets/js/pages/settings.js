document.addEventListener("DOMContentLoaded", function() {
        loadCompanyData();

        document.querySelector("form").addEventListener("submit", function(e) {
            e.preventDefault();
            saveCompany();
        });
    });

function loadCompanyData() {
    const apiKey = prompt("Please enter your API key (Fill blank if first time registering a company):");
    if (!apiKey) return;

    fetch(`http://localhost:8080/api/company?apiKey=${encodeURIComponent(apiKey)}`)
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            // If company doesn't exist
            return null;
        })
        .then(company => {
            if (company) {
                createForm(company);
            }
        });
    }

function createForm(company) {
        // Map form fields to company data
    document.getElementById("company-name").value = company.companyName || '';
    document.getElementById("contact-person").value = company.contactPerson || '';
    document.getElementById("address").value = company.address || '';
    document.getElementById("phone-number").value = company.phoneNumber || '';
    document.getElementById("fax-number").value = company.faxNumber || '';
    document.getElementById("email").value = company.email || '';
    document.getElementById("website").value = company.website || '';
    document.getElementById("trade-authorization-number").value = company.tradeAuthorizationNumber || '';
    document.getElementById("api-key").value = company.apiKey || '';
}

function saveCompany() {
    const companyData = {
        companyName: document.getElementById("company-name").value,
        contactPerson: document.getElementById("contact-person").value,
        address: document.getElementById("address").value,
        phoneNumber: document.getElementById("phone-number").value,
        faxNumber: document.getElementById("fax-number").value,
        email: document.getElementById("email").value,
        website: document.getElementById("website").value,
        tradeAuthorizationNumber: document.getElementById("trade-authorization-number").value,
        apiKey: document.getElementById("api-key").value
    };

    fetch("http://localhost:8080/api/company", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(companyData)
    })
    .then(response => {
        if (response.ok) {
            alert("Company settings saved successfully!");
        }
    });
}
