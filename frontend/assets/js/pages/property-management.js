let properties = [];
let apiKey = '';

const propertyTableBody = document.querySelector("#dataTable tbody");
const searchInput = document.querySelector("input[type='search']");

const addPropertyModal = document.getElementById("modal-2");
const managePropertyModal = document.getElementById("modal-1");
const deletePropertyModal = document.getElementById("modal-3");

const titleInput = document.getElementById("title");
const descriptionInput = document.getElementById("description");
const cityInput = document.getElementById("city");
const countyInput = document.getElementById("county");
const districtInput = document.getElementById("district");
const fullAddressInput = document.getElementById("full-address");
const floorInput = document.getElementById("floor");
const buildingFloorsInput = document.getElementById("building-floors");
const netM2Input = document.getElementById("net-m2");
const numberOfRoomsInput = document.getElementById("number-of-rooms");
const numberOfHallsInput = document.getElementById("number-of-halls");
const priceInput = document.getElementById("price");
const heatingTypeSelect = document.getElementById("heating-type");
const propertyTypeSelect = document.querySelector("select[id*='property-type']");
const propertyStatusSelect = document.querySelector("select[id*='property-status']");
const ownerIdInput = document.querySelector("input[placeholder='Owner ID (Required)']");
const tenantIdInput = document.querySelector("input[placeholder='Tenant ID (Not Required)']");
const addApiKeyInput = document.querySelector("#modal-2 input[placeholder='API Key..']");

const manageStatusSelect = document.querySelector("#modal-1 select");
const manageOwnerIdInput = document.querySelector("#modal-1 input[placeholder='Owner ID']");
const manageTenantIdInput = document.querySelector("#modal-1 input[placeholder='Tenant ID']");
const manageApiKeyInput = document.querySelector("#modal-1 input[placeholder='API Key..']");

const deleteApiKeyInput = document.querySelector("#modal-3 input[type='text']");

document.addEventListener("DOMContentLoaded", function() {
    loadProperties();
    setupEventListeners();
});

function setupEventListeners() {
    const saveButton = document.querySelector("#modal-2 .btn-primary:last-child");
    saveButton.addEventListener('click', handleAddProperty);

    const manageSaveButton = document.querySelector("#modal-1 .btn-primary:last-child");
    manageSaveButton.addEventListener("click", handleManageProperty);

    const deleteButton = document.querySelector("#modal-3 .btn-primary:last-child");
    deleteButton.addEventListener("click", handleDeleteProperty);

    searchInput.addEventListener("input", handleSearch);
}

async function loadProperties() {
    try {
        const response = await fetch(`${BASE_URL}/api/properties`);
        if (response.ok) {
            properties = await response.json();
            renderPropertiesTable();
        }
    } catch (error) {
        console.error(error);
    }
}

function renderPropertiesTable(propertiesToShow = properties) {
    propertyTableBody.innerHTML = '';

    propertiesToShow.forEach(property => {
        const row = document.createElement("tr");
        const address = `${property.addressDistrict}/${property.addressCounty}/${property.addressCity}`;

        row.innerHTML = `
            <td>${property.id}</td>
            <td>${address}</td>
            <td>${property.ownerId}</td>
            <td>${property.tenantId || '-'}</td>
            <td>${property.propertyType}</td>
            <td>${property.propertyStatus}</td>
            <td>${property.priceDisplay || property.price + ' TRY'}</td>
            <td>
                <button class="btn btn-danger border rounded-0" type="button" onclick="showDeleteModal(${property.id})">
                    <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-x">
                        <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"></path>
                    </svg>Delete
                </button>
                <button class="btn btn-primary border rounded-0" type="button" onclick="showManageModal(${property.id})" style="margin-left: 0;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-gear">
                        <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"></path>
                        <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"></path>
                    </svg>&nbsp;Manage
                </button>
            </td>
        `;
        propertyTableBody.appendChild(row);
    });
}

async function handleAddProperty() {
    const propertyData = {
        title: titleInput.value,
        description: descriptionInput.value,
        addressCity: cityInput.value,
        addressCounty: countyInput.value,
        addressDistrict: districtInput.value,
        addressFull: fullAddressInput.value,
        floor: parseInt(floorInput.value),
        buildingFloors: parseInt(buildingFloorsInput.value),
        areaNetSquaremeter: parseInt(netM2Input.value),
        numberOfRooms: parseInt(numberOfRoomsInput.value),
        numberOfHalls: parseInt(numberOfHallsInput.value),
        price: parseFloat(priceInput.value),
        heatingType: heatingTypeSelect.value,
        propertyType: propertyTypeSelect.value,
        propertyStatus: propertyStatusSelect.value,
        ownerId: parseInt(ownerIdInput.value),
        tenantId: tenantIdInput.value ? parseInt(tenantIdInput.value) : null
    };

    apiKey = addApiKeyInput.value;

    try {
        const response = await fetch(`${BASE_URL}/api/properties?apiKey=${apiKey}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(propertyData)
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(addPropertyModal);
            modal.hide();
            clearAddPropertyForm();
            loadProperties();
        } else {
            alert("Failed to create property. Check your API key.");
        }
    } catch (error) {
        alert("Error creating property");
    }
}

let propertyToManage = null;
function showManageModal(propertyId) {
    propertyToManage = propertyId;
    const property = properties.find(p => p.id === propertyId);
    if (!property) return;
    manageStatusSelect.value = property.propertyStatus;
    manageOwnerIdInput.value = property.ownerId;
    manageTenantIdInput.value = property.tenantId || '';

    const modal = new bootstrap.Modal(managePropertyModal);
    modal.show();
}

async function handleManageProperty() {
    if (!propertyToManage) return;

    apiKey = manageApiKeyInput.value.trim();
    if (!apiKey) {
        alert("Please provide API key");
        return;
    }

    const updateData = {
        propertyStatus: manageStatusSelect.value,
        ownerId: parseInt(manageOwnerIdInput.value),
        tenantId: manageTenantIdInput.value ? parseInt(manageTenantIdInput.value) : null
    };

    try {
        const response = await fetch(`${BASE_URL}/api/properties/${propertyToManage}?apiKey=${apiKey}`, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updateData)
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(managePropertyModal);
            modal.hide();
            manageApiKeyInput.value = "";
            propertyToManage = null;
            loadProperties();
        } else {
            alert("Failed to update property. Check your API key.");
        }
    } catch (error) {
        alert("Error updating property");
    }
}

let propertyToDelete = null;
function showDeleteModal(propertyId) {
    propertyToDelete = propertyId;
    const modal = new bootstrap.Modal(deletePropertyModal);
    modal.show();
}

async function handleDeleteProperty() {
    if (!propertyToDelete) return;

    apiKey = deleteApiKeyInput.value.trim();
    if (!apiKey) {
        alert("Please provide API key");
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/api/properties/${propertyToDelete}?apiKey=${apiKey}`, {
            method: "DELETE"
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(deletePropertyModal);
            modal.hide();
            deleteApiKeyInput.value = "";
            propertyToDelete = null;
            loadProperties();
        } else {
            alert("Failed to delete property. Check your API key.");
        }
    } catch (error) {
        alert("Error deleting property");
    }
}

function handleSearch() {
    const searchTerm = searchInput.value.toLowerCase().trim();

    if (!searchTerm) {
        renderPropertiesTable(properties);
        return;
    }

    const filteredProperties = properties.filter(property =>
        property.id.toString().includes(searchTerm) ||
        property.addressCity.toLowerCase().includes(searchTerm) ||
        property.addressCounty.toLowerCase().includes(searchTerm) ||
        property.addressDistrict.toLowerCase().includes(searchTerm) ||
        property.ownerId.toString().includes(searchTerm) ||
        (property.tenantId && property.tenantId.toString().includes(searchTerm)) ||
        property.propertyType.toLowerCase().includes(searchTerm) ||
        property.propertyStatus.toLowerCase().includes(searchTerm) ||
        property.price.toString().includes(searchTerm)
    );

    renderPropertiesTable(filteredProperties);
}

function clearAddPropertyForm() {
    titleInput.value = "";
    descriptionInput.value = "";
    cityInput.value = "";
    countyInput.value = "";
    districtInput.value = "";
    fullAddressInput.value = "";
    floorInput.value = "";
    buildingFloorsInput.value = "";
    netM2Input.value = "";
    numberOfRoomsInput.value = "";
    numberOfHallsInput.value = "";
    priceInput.value = "";
    heatingTypeSelect.value = "NATURAL_GAS";
    propertyTypeSelect.value = "HOUSE";
    propertyStatusSelect.value = "RENTAL";
    ownerIdInput.value = "";
    tenantIdInput.value = "";
    addApiKeyInput.value = "";
}
