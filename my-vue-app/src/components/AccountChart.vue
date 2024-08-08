<!-- src/components/AccountChart.vue -->
<template>
  <div class="chart-container">
    <h2>수입/지출 그래프</h2>
    <div v-if="chartData.labels.length">
      <Bar :data="chartData" :options="chartOptions" />
    </div>
    <p v-else>데이터가 없습니다.</p>
  </div>
</template>

<script>
import { Bar } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

export default {
  components: {
    Bar
  },
  props: {
    accounts: {
      type: Array,
      required: true
    }
  },
  computed: {
    chartData() {
      const labels = [];
      const incomeData = [];
      const expenseData = [];

      this.accounts.forEach(account => {
        const date = new Date(account.date).toLocaleDateString('en-US', { month: 'short', year: 'numeric' });
        const amount = account.amount;

        if (!labels.includes(date)) {
          labels.push(date);
          incomeData.push(0);
          expenseData.push(0);
        }

        const index = labels.indexOf(date);
        if (account.type === 'INCOME') {
          incomeData[index] += amount;
        } else if (account.type === 'EXPENSE') {
          expenseData[index] += amount;
        }
      });

      return {
        labels: labels,
        datasets: [
          {
            label: '수입',
            backgroundColor: '#42A5F5',
            data: incomeData
          },
          {
            label: '지출',
            backgroundColor: '#FF7043',
            data: expenseData
          }
        ]
      };
    },
    chartOptions() {
      return {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'top'
          },
          tooltip: {
            callbacks: {
              label: function(context) {
                let label = context.dataset.label || '';
                if (label) {
                  label += ': ';
                }
                if (context.parsed.y !== null) {
                  label += `${context.parsed.y} 원`;
                }
                return label;
              }
            }
          }
        }
      };
    }
  }
};
</script>

<style scoped>
.chart-container {
  max-width: 800px;
  margin: 20px auto;
}
</style>
