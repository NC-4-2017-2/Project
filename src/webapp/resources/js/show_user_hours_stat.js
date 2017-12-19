function init(workingHoursStatisticList) {
  var options = {
        chart: {
          renderTo: 'container',
          type: 'pie'
        },
        title: {
          text: 'User Working Hours Statistics'
        },
        tooltip: {
          pointFormat: '{series.name}: <b>{point.y} hours</b>'
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
          name: 'Hours Count',
          colorByPoint: true,
          data: [
              for(i in workingHoursStatisticList) {
            name: workingHoursStatisticList.workingDayDate[i],
            y: workingHoursStatisticList.hoursCount[i]
          }]

    //       <c:forEach items="${workingHoursStatisticList}" var="workingHours">
    //   {
    //     name: '${workingHours.workingDayDate}',
    //     y: ${workingHours.hoursCount}
    //   },
    // </c:forEach>]
    }]
    };
      var chart = new Highcharts.Chart(options);
    }