// ------------------------------
// Display Projects
// ------------------------------
function displayProjects(projectsList) {
  const container = document.getElementById("projectsContainer");
  container.innerHTML = "";

  projectsList.forEach(project => {
    const card = document.createElement("div");
    card.classList.add("project-card");

    card.innerHTML = `
      <img src="${project.image}" alt="${project.title}">
      <h3>${project.title}</h3>
      <p>${project.description}</p>
      <span class="category-badge">${project.category}</span>
      <a href="${project.link}" target="_blank">View Project</a>
    `;

    container.appendChild(card);
  });

  updateCounter(projectsList.length);
}

// ------------------------------
// Filtering System
// ------------------------------
function filterProjects(category) {
  if (category === "all") {
    displayProjects(projects);
  } else {
    const filtered = projects.filter(p => p.category === category);
    displayProjects(filtered);
  }
}

// ------------------------------
// Filter Button Click Listeners
// ------------------------------
document.querySelectorAll(".filter-btn").forEach(btn => {
  btn.addEventListener("click", () => {
    document.querySelector(".active").classList.remove("active");
    btn.classList.add("active");

    const category = btn.dataset.category;
    filterProjects(category);
  });
});

// ------------------------------
// Project Counter
// ------------------------------
function updateCounter(count) {
  const total = projects.length;
  document.getElementById("projectCounter").textContent =
    `Showing ${count} of ${total} projects`;
}

// ------------------------------
// Initial Load (show only 4)
// ------------------------------
displayProjects(projects);
