function init(critical, high, normal, low) {
  var options = {
    chart: {
      renderTo: 'container',
      type: 'pie'
    },
    title: {
      text: 'User Task Priority Statistic'
    },
    tooltip: {
      pointFormat: '{series.name}: <b>{point.y} tasks</b>'
    },
    plotOptions: {
      pie: {
        allowPointSelect: true,
        cursor: 'pointer',
        dataLabels: {
          enabled: true,
          format: '<b>{point.name}</b>: {point.percentage:.1f} %',
          style: {
            color: (Highcharts.theme && Highcharts.theme.contrastTextColor)
            || 'black'
          }
        }
      }
    },
    series: [{
      name: 'Percentage',
      colorByPoint: true,
      data: [{
        name: 'Critical',
        y: (critical == 0) ? null : critical
      }, {
        name: 'High',
        y: (high == 0) ? null : high,
        sliced: true,
        selected: true
      }, {
        name: 'Normal',
        y: (normal == 0) ? null : normal
      }, {
        name: 'Low',
        y: (low == 0) ? null : low
      }]
    }]
  };
  var chart = new Highcharts.Chart(options);
}