document.addEventListener('DOMContentLoaded', () => {
    const sidebarItems = document.querySelectorAll('.sidebar-item');
    const moduleContents = document.querySelectorAll('.module-content');
    const userProfile = document.querySelector('.user-profile');
    const profileDropdown = document.querySelector('.profile-dropdown');

    // Handle sidebar navigation
    sidebarItems.forEach(item => {
        item.addEventListener('click', () => {
            // Remove active class from all sidebar items
            sidebarItems.forEach(i => i.classList.remove('active'));
            // Add active class to clicked item
            item.classList.add('active');

            // Hide all module contents
            moduleContents.forEach(content => content.classList.remove('active'));

            // Show the corresponding module content
            const moduleId = item.dataset.module + '-module';
            const activeModule = document.getElementById(moduleId);
            if (activeModule) {
                activeModule.classList.add('active');
            }
        });
    });

    // Handle user profile dropdown
    if (userProfile && profileDropdown) {
        userProfile.addEventListener('click', (event) => {
            profileDropdown.classList.toggle('active');
            event.stopPropagation(); // Prevent click from closing immediately
        });

        // Close dropdown when clicking outside
        document.addEventListener('click', (event) => {
            if (!userProfile.contains(event.target) && !profileDropdown.contains(event.target)) {
                profileDropdown.classList.remove('active');
            }
        });
    }

    // --- Dynamic Module Loading (Simplified) ---
    // In a real SPA, you'd use a router or more sophisticated module loading.
    // Here, we just activate the dashboard by default.
    const initialModule = document.querySelector('.sidebar-item.active');
    if (initialModule) {
        const initialModuleId = initialModule.dataset.module + '-module';
        const initialActiveModule = document.getElementById(initialModuleId);
        if (initialActiveModule) {
            initialActiveModule.classList.add('active');
        }
    }
});

// Helper functions (can be moved to a separate 'utils.js' if preferred)
function showModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'flex';
    }
}

function hideModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'none';
    }
}