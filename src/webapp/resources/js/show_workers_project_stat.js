function init(working, fired) {
  var options = {
    chart: {
      renderTo: 'container',
      type: 'pie'
    },
    title: {
      text: 'Project Workers Statistic'
    },
    tooltip: {
      pointFormat: '{series.name}: <b>{point.y} peoples</b>'
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
        name: 'Working',
        y:(working == 0) ? null : working
      }, {
        name: 'Worked',
        y: (fired == 0) ? null : fired,
        sliced: true,
        selected: true
      }]
    }]
  };
  var chart = new Highcharts.Chart(options);

}