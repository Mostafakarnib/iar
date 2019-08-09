var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var url = "http://" + window.location.host + context;

var myChart = null;

function drawAllCharts()
{
    var graphs = OmniFaces.Ajax.data.graph_list;
    for (var i = 0; i < graphs.length; i++) {
        previewGraph(graphs[i]);
    }

}

$(document).on('click', '.graph-type', function () {
    $('.graph-type').removeClass('selected');
    $(this).addClass('selected');
    var type = $(this).attr('data-type');
    $('.graph-type-value').val(type);
    $('.graph-type-value').click();
    $('.graph-type-value').change();
});

$(document).on('click', '.toggle-graph-settings', function () {
    console.log('toggle clicked!!!');
    $(this).closest('.box').find('.graph-settings').toggleClass('active');

});

$(document).on('click', '.close-graph-settings', function () {
    console.log('close clicked!!!');
    $(this).closest('.box').find('.graph-settings').removeClass('active');

});



function previewGraph(graph) {
    console.log(graph.type);

    var target = $('#' + graph.id);
    var type = graph.type;

    if (type === 'chartjs_bar' || type === 'chartjs_line')
    {
        var labels = graph.labels;
        drawBarChart(labels, graph.areaDatasets, target);
    }
    if (type === 'chartjs_donut')
    {
        drawDonut(graph.donutDatasets, target);
    }
}

function drawLineChart(preparedLabels, preparedDatasets, target)
{
    var lineChartData = {
        labels: preparedLabels,
        datasets: preparedDatasets
    };
    var lineChartCanvas = target.get(0).getContext('2d');
    var lineChart = new Chart(lineChartCanvas);
    var lineChartOptions = {
        //Boolean - If we should show the scale at all
        showScale: true,
        //Boolean - Whether grid lines are shown across the chart
        scaleShowGridLines: false,
        //String - Colour of the grid lines
        scaleGridLineColor: 'rgba(0,0,0,.05)',
        //Number - Width of the grid lines
        scaleGridLineWidth: 1,
        //Boolean - Whether to show horizontal lines (except X axis)
        scaleShowHorizontalLines: true,
        //Boolean - Whether to show vertical lines (except Y axis)
        scaleShowVerticalLines: true,
        //Boolean - Whether the line is curved between points
        bezierCurve: true,
        //Number - Tension of the bezier curve between points
        bezierCurveTension: 0.3,
        //Boolean - Whether to show a dot for each point
        pointDot: false,
        //Number - Radius of each point dot in pixels
        pointDotRadius: 4,
        //Number - Pixel width of point dot stroke
        pointDotStrokeWidth: 1,
        //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
        pointHitDetectionRadius: 20,
        //Boolean - Whether to show a stroke for datasets
        datasetStroke: true,
        //Number - Pixel width of dataset stroke
        datasetStrokeWidth: 2,
        //Boolean - Whether to fill the dataset with a color
        datasetFill: true,
        //String - A legend template
        legendTemplate: '<ul class="<%=name.toLowerCase()%>-legend"><% for (var i=0; i<datasets.length; i++){%><li><span style="background-color:<%=datasets[i].lineColor%>"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>',
        //Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
        maintainAspectRatio: true,
        //Boolean - whether to make the chart responsive to window resizing
        responsive: true,

        tooltipFillColor: 'rgba(0,0,0,0.8)',
        multiTooltipTemplate: '<%= datasetLabel %>'
    };
    lineChartOptions.datasetFill = false;
    lineChart.line(lineChartData, lineChartOptions);
}


function drawBarChart(preparedLabels, preparedDatasets, target)
{
    var barChartData = {
        labels: preparedLabels,
        datasets: preparedDatasets
    };
    var barChartCanvas = target.get(0).getContext('2d');
    var barChart = new Chart(barChartCanvas);
    var barChartOptions = {
        //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
        scaleBeginAtZero: true,
        //Boolean - Whether grid lines are shown across the chart
        scaleShowGridLines: true,
        //String - Colour of the grid lines
        scaleGridLineColor: 'rgba(0,0,0,.05)',
        //Number - Width of the grid lines
        scaleGridLineWidth: 1,
        //Boolean - Whether to show horizontal lines (except X axis)
        scaleShowHorizontalLines: true,
        //Boolean - Whether to show vertical lines (except Y axis)
        scaleShowVerticalLines: true,
        //Boolean - If there is a stroke on each bar
        barShowStroke: true,
        //Number - Pixel width of the bar stroke
        barStrokeWidth: 2,
        //Number - Spacing between each of the X value sets
        barValueSpacing: 5,
        //Number - Spacing between data sets within X values
        barDatasetSpacing: 1,
        //Boolean - whether to make the chart responsive
        responsive: true,
        maintainAspectRatio: true,
        tooltipFillColor: 'rgba(0,0,0,0.8)',
        multiTooltipTemplate: '<%= datasetLabel %>'
    };
    barChartOptions.datasetFill = true;
    barChart.Bar(barChartData, barChartOptions);
}


function drawDonut(donut_data, target) {
    var pieChartCanvas = target.get(0).getContext('2d');
    var pieChart = new Chart(pieChartCanvas);
    var PieData = donut_data;
    var pieOptions = {
        //Boolean - Whether we should show a stroke on each segment
        segmentShowStroke: true,
        //String - The colour of each segment stroke
        segmentStrokeColor: '#fff',
        //Number - The width of each segment stroke
        segmentStrokeWidth: 2,
        //Number - The percentage of the chart that we cut out of the middle
        percentageInnerCutout: 50, // This is 0 for Pie charts
        //Number - Amount of animation steps
        animationSteps: 100,
        //String - Animation easing effect
        animationEasing: 'easeOutBounce',
        //Boolean - Whether we animate the rotation of the Doughnut
        animateRotate: true,
        //Boolean - Whether we animate scaling the Doughnut from the centre
        animateScale: false,
        //Boolean - whether to make the chart responsive to window resizing
        responsive: true,
        // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
        maintainAspectRatio: true
    }
    pieChart.Doughnut(PieData, pieOptions);
}


function updateGraph(data) {
    if (data.status === 'success') {
        var graphId = $(data.source).closest('.box').find('.chart').attr('data-graph-id');
        $(data.source).closest('.box').find('.graph-settings').removeClass('active');

        $.ajax({
            url: url + '/graphs?graphId=' + graphId,
            contentType: 'application/json',
            success: function (data) {
                console.log(data);
                previewGraph(data);

            },
            error: function (error) {
                console.log(error);
            }
        });
    }
}

