let properties = [];

const propertyTableBody = document.querySelector("#dataTable tbody");
const keywordSearchInput = document.querySelector("input[type='text']");
const propertyTypeSelect = document.querySelector("select:nth-of-type(1)");
const propertyStatusSelect = document.querySelector("select:nth-of-type(2)");
const heatingTypeSelect = document.querySelector("select:nth-of-type(3)");
const searchButton = document.querySelector("button:first-of-type");
const filterButton = document.querySelector("button:last-of-type");
const propertyDetailsModal = document.getElementById("modal-1");

document.addEventListener("DOMContentLoaded", function() {
    loadAllProperties();
    setupEventListeners();
});

function setupEventListeners() {
    searchButton.addEventListener('click', handleKeywordSearch);
    filterButton.addEventListener('click', handleFilterSearch);
}

async function loadAllProperties() {
    try {
        const response = await fetch(`${BASE_URL}/api/properties`);
        if (response.ok) {
            properties = await response.json();
            renderPropertiesTable(properties);
        }
    } catch (error) {
        console.error(error);
    }
}

async function handleKeywordSearch() {
    const keyword = keywordSearchInput.value.trim().toLowerCase();
    if (!keyword) {
        loadAllProperties();
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/api/properties/search?keyword=${encodeURIComponent(keyword)}`);
        if (response.ok) {
            const searchResults = await response.json();
            renderPropertiesTable(searchResults);
            keywordSearchInput.value = "";
        }
    } catch (error) {
        console.error(error);
    }
}

async function handleFilterSearch() {
    const selectedPropertyType = propertyTypeSelect.value;
    const selectedPropertyStatus = propertyStatusSelect.value;
    const selectedHeatingType = heatingTypeSelect.value;

    const params = new URLSearchParams();

    if (selectedPropertyType !== 'NONE') {
        params.append('propertyType', selectedPropertyType);
    }
    if (selectedPropertyStatus !== 'NONE') {
        params.append('propertyStatus', selectedPropertyStatus);
    }
    if (selectedHeatingType !== 'NONE') {
        params.append('heatingType', selectedHeatingType);
    }

    try {
        const queryString = params.toString();
        const url = `${BASE_URL}/api/properties/filter${queryString ? '?' + queryString : ''}`;

        const response = await fetch(url);
        if (response.ok) {
            const filterResults = await response.json();
            renderPropertiesTable(filterResults);
        }
    } catch (error) {
        console.error(error);
    }
}

function renderPropertiesTable(propertiesToShow) {
    propertyTableBody.innerHTML = '';

    if (!propertiesToShow || propertiesToShow.length === 0) {
        const row = document.createElement("tr");
        row.innerHTML = `<td colspan="7" class="text-center">No properties found</td>`;
        propertyTableBody.appendChild(row);
        return;
    }

    propertiesToShow.forEach(property => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${property.id}</td>
            <td>${property.title}</td>
            <td>${property.addressCity}</td>
            <td>${property.priceDisplay}</td>
            <td>${property.propertyType}</td>
            <td>${property.propertyStatus}</td>
            <td>
                <button class="btn btn-primary border rounded-0" type="button" onclick="showPropertyDetails(${property.id})" data-bs-target="#modal-1" data-bs-toggle="modal">
                    <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-eye">
                        <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"></path>
                        <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0"></path>
                    </svg>&nbsp;View
                </button>
            </td>
        `;
        propertyTableBody.appendChild(row);
    });
}

function showPropertyDetails(propertyId) {
    let property = null;
    const currentRows = propertyTableBody.querySelectorAll('tr');
    for (let row of currentRows) {
        const idCell = row.querySelector('td:first-child');
        if (idCell && parseInt(idCell.textContent) === propertyId) {
            break;
        }
    }

    fetchPropertyDetails(propertyId);
}

async function fetchPropertyDetails(propertyId) {
    try {
        const response = await fetch(`${BASE_URL}/api/properties/${propertyId}`);
        if (response.ok) {
            const property = await response.json();
            displayPropertyDetails(property);
        }
    } catch (error) {
        console.error(error);
    }
}

function displayPropertyDetails(property) {
    const detailsTable = document.querySelector("#modal-1 .table-striped-columns tbody");
    detailsTable.innerHTML = `
        <tr><td>Title</td><td>${property.title}</td></tr>
        <tr><td>Description</td><td>${property.description}</td></tr>
        <tr><td>City</td><td>${property.addressCity}</td></tr>
        <tr><td>County</td><td>${property.addressCounty}</td></tr>
        <tr><td>District</td><td>${property.addressDistrict}</td></tr>
        <tr><td>Full Address</td><td>${property.addressFull}</td></tr>
        <tr><td>Floor</td><td>${property.floor} / ${property.buildingFloors}</td></tr>
        <tr><td>Net m2</td><td>${property.areaNetSquaremeter}</td></tr>
        <tr><td>Number of Rooms</td><td>${property.numberOfRooms}</td></tr>
        <tr><td>Number of Halls</td><td>${property.numberOfHalls}</td></tr>
        <tr><td>Heating Type</td><td>${property.heatingType}</td></tr>
        <tr><td>Property Type</td><td>${property.propertyType}</td></tr>
        <tr><td>Property Status</td><td>${property.propertyStatus}</td></tr>
        <tr><td>Price</td><td>${property.priceDisplay}</td></tr>
    `;
}
