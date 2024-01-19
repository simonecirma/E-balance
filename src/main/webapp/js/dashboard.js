function toggleExpansion(index) {
    const sections = document.querySelectorAll('.dashboard .section');

    sections.forEach((section, i) => {
        if (i + 1 === index) {
            section.classList.toggle('section-expanded');
        } else {
            section.classList.remove('section-expanded');
        }
    });

    document.querySelectorAll('.dashboard .section:not(.section-expanded)').forEach((section) => {
        section.style.display = 'none';
    });

    // Mostra tutti i div quando nessuno è espanso
    if (!document.querySelector('.dashboard .section-expanded')) {
        document.querySelectorAll('.dashboard .section').forEach((section) => {
            section.style.display = 'block';
        });
    }
}
const chartData = {
    labels: ["Python", "Java", "JavaScript", "C#", "Others"],
    data: [30, 17, 10, 7, 36],
};

const myChart = document.querySelector(".my-chart");
const ul = document.querySelector(".programming-stats .details ul");

new Chart(myChart, {
    type: "doughnut",
    data: {
        labels: chartData.labels,
        datasets: [
            {
                label: "Language Popularity",
                data: chartData.data,
            },
        ],
    },
    options: {
        borderWidth: 10,
        borderRadius: 2,
        hoverBorderWidth: 0,
        plugins: {
            legend: {
                display: false,
            },
        },
    },
});

const populateUl = () => {
    chartData.labels.forEach((l, i) => {
        let li = document.createElement("li");
        li.innerHTML = `${l}: <span class='percentage'>${chartData.data[i]}%</span>`;
        ul.appendChild(li);
    });
};

populateUl();

</script>
