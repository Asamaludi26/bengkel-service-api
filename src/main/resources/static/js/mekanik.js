document.addEventListener("DOMContentLoaded", function () {
    fetch("/mekanik")
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector("#mekanikTable tbody");
            data.forEach((mekanik, index) => {
                const row = `<tr>
                    <td>${index + 1}</td>
                    <td>${mekanik.nama}</td>
                    <td>${mekanik.keahlian}</td>
                    <td>${mekanik.nomorTelepon}</td>
                    <td>${mekanik.status}</td>
                </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching data:", error));
});