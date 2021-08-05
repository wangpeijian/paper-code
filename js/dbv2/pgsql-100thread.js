option = {
    color: ["#333", "#e01f54", '#e87c25', '#3fb1e3', '#6be6c1'],
    title: {
        text: 'pgsql-100并发'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['ssi', 'redis_rr', 'redis_rc', 'java_rr', 'java_rc']
    },
    grid: {
        top: '10%',
        left: '3%',
        right: '10%',
        bottom: '3%',
        containLabel: true
    },

    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['0.6', '0.8', '1.0', '1.2', '1.4'],
        name: 'zipf系数'
    },
    yAxis: {
        type: 'value',
        min: 40,
        max: 170,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: 'ssi',
            type: 'line',

            data: [130.53777, 125.97, 112.4504, 76.9257, 65.6667]
        },
        {
            name: 'redis_rr',
            type: 'line',

            data: [140.97333, 137.65515, 97.76736, 60.28965, 50.72036]
        },
        {
            name: 'redis_rc',
            type: 'line',

            data: [145.39805, 140.7, 106.8298, 65.52006, 56.1975]
        },
        {
            name: 'java_rr',
            type: 'line',

            data: [162.4748, 160.70865, 129, 79.79202, 67.39857]
        },
        {
            name: 'java_rc',
            type: 'line',

            data: [166.7, 163, 130.75902, 83.11603, 69.5]
        },
    ]
};