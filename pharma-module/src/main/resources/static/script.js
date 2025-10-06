document.addEventListener('DOMContentLoaded', function() {
    const medicineForm = document.getElementById('medicineForm');
    const medicineTableBody = document.querySelector("#medicinesTable tbody");
    const medicineIdField = document.getElementById('medicineId');
    const formTitle = document.getElementById('form-title');
    const submitButton = document.getElementById('submit-button');
    const cancelButton = document.getElementById('cancel-button');

    const apiBaseUrl = '/medicines';

    // Fetches all medicines from the backend and renders them in the table
    const fetchAndRenderMedicines = async () => {
        try {
            const response = await fetch(`${apiBaseUrl}/list`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const medicines = await response.json();
            
            // Clear existing table rows
            medicineTableBody.innerHTML = ''; 
            
            medicines.forEach(medicine => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${medicine.name}</td>
                    <td>${medicine.description}</td>
                    <td>$${medicine.price.toFixed(2)}</td>
                    <td>
                        <button class="action-btn edit-btn" data-id="${medicine.id}">Edit</button>
                        <button class="action-btn delete-btn" data-id="${medicine.id}">Delete</button>
                    </td>
                `;
                medicineTableBody.appendChild(row);
            });
        } catch (error) {
            console.error("Failed to fetch medicines:", error);
            medicineTableBody.innerHTML = `<tr><td colspan="4">Error loading data.</td></tr>`;
        }
    };
    
    // Resets the form to its initial "Add" state
    const resetForm = () => {
        medicineForm.reset();
        medicineIdField.value = '';
        formTitle.textContent = 'Add New Medicine';
        submitButton.textContent = 'Save Medicine';
        cancelButton.style.display = 'none';
        medicineForm.action = apiBaseUrl; // Default action for adding
    };

    // Handles form submission for both adding and updating
    medicineForm.addEventListener('submit', async function(event) {
        event.preventDefault();
        
        const isUpdate = !!medicineIdField.value;
        const url = isUpdate ? `${apiBaseUrl}/update` : apiBaseUrl;
        const formData = new FormData(medicineForm);

        try {
            const response = await fetch(url, {
                method: 'POST',
                body: new URLSearchParams(formData) // Spring Boot @ModelAttribute expects form data
            });
            
            // Because the controller redirects, the response might not be JSON.
            // We just need to know if it was successful (status 2xx).
            if (response.ok) {
                resetForm();
                fetchAndRenderMedicines(); // Refresh the table
            } else {
                 throw new Error(`Server responded with status: ${response.status}`);
            }
        } catch (error) {
            console.error('Failed to save medicine:', error);
            alert('Error: Could not save the medicine.');
        }
    });

    // Event delegation to handle clicks on "Edit" and "Delete" buttons
    medicineTableBody.addEventListener('click', async function(event) {
        const target = event.target;
        const id = target.getAttribute('data-id');

        // Handle Edit Button Click
        if (target.classList.contains('edit-btn')) {
            try {
                const response = await fetch(`${apiBaseUrl}/${id}`);
                if (!response.ok) throw new Error('Failed to fetch medicine details.');
                
                const medicine = await response.json();

                // Populate the form with the medicine's data
                document.getElementById('medicineId').value = medicine.id;
                document.getElementById('name').value = medicine.name;
                document.getElementById('description').value = medicine.description;
                document.getElementById('price').value = medicine.price;

                // Update UI for editing
                formTitle.textContent = 'Edit Medicine';
                submitButton.textContent = 'Update Medicine';
                cancelButton.style.display = 'inline-block';
                window.scrollTo(0, 0); // Scroll to top to see the form
            } catch (error) {
                 console.error('Failed to edit medicine:', error);
                 alert('Error: Could not load medicine data for editing.');
            }
        }
        
        // Handle Delete Button Click
        if (target.classList.contains('delete-btn')) {
            if (confirm('Are you sure you want to delete this medicine?')) {
                try {
                    const formData = new URLSearchParams();
                    formData.append('id', id);

                    const response = await fetch(`${apiBaseUrl}/delete`, {
                        method: 'POST',
                        body: formData
                    });

                    if (response.ok) {
                        fetchAndRenderMedicines(); // Refresh table
                    } else {
                        throw new Error(`Server responded with status: ${response.status}`);
                    }
                } catch(error) {
                    console.error('Failed to delete medicine:', error);
                    alert('Error: Could not delete the medicine.');
                }
            }
        }
    });
    
    // Handle Cancel Button Click
    cancelButton.addEventListener('click', resetForm);

    // Initial load of medicines when the page is ready
    fetchAndRenderMedicines();
});