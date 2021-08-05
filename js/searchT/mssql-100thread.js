option = {
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],
    title: {
        text: 'mssql-100并发'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['ssi', 'redis_rr', 'redis_rc', 'redis_ru', 'java_rr', 'java_rc', 'java_ru']
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
        data: ['关闭','0', '100', '500'],
        name: '事务阻塞（ms）'
    },
    yAxis: {
        type: 'value',
        min: 40,
        max: 130,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: 'ssi',
            type: 'line',
            data: [77.11459,73.38786, 62.1291, 41.13508]
        },
        {
            name: 'redis_rr',
            type: 'line',
            data: [107.38533,101.9011,92.67495, 61.69287]
        },
        {
            name: 'redis_rc',
            type: 'line',
            data: [108.51394,105.69548,100.29625, 83.89173]
        },
        {
            name: 'redis_ru',
            type: 'line',
            data: [109.85568,107.10315, 101.64432, 84.26607]
        },
        {
            name: 'java_rr',
            type: 'line',
            data: [120.74484,117.32014, 107.3761,80.19672 ]
        },
        {
            name: 'java_rc',
            type: 'line',
            data: [122.0847,121.62024,  115.30264, 99.24288]
        },
        {
            name: 'java_ru',
            type: 'line',
            data: [124.54296,124.25424,115.9908, 100.6096]
        },
    ]
};