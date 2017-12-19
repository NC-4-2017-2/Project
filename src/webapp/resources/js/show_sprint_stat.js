function init(sprintStatList, plannedDateList, currentEndList) {
  var options = {
    chart: {
      renderTo: 'container',
      type: 'line'
    },
    title: {
      text: 'Sprint Statistic'
    },
    subtitle: {
      text: ''
    },
    xAxis: {
      categories: [sprintStatList]
    },
    legend: {
      layout: 'vertical',
      align: 'right',
      verticalAlign: 'middle'
    },
    yAxis: {
      title: {
        text: 'Number of Days'
      }
    },
    plotOptions: {
      series: {
        label: {
          connectorAllowed: false
        }
      }
    },
    series: [

      {
        name: 'Planned',
        data: plannedDateList
      },

      {
        name: 'Actual',
        data: currentEndList
      }
    ],

    responsive: {
      rules: [{
        condition: {
          maxWidth: 500
        },
        chartOptions: {
          legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom'
          }
        }
      }]
    }
  };
  var chart = new Highcharts.Chart(options);
}