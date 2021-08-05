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
        data: ['关闭','0', '100', '500'],
        name: '事务阻塞（ms）'
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
            data: [113.59024,112.4504, 108.66648, 100.36544]
        },
        {
            name: 'redis_rr',
            type: 'line',
            data: [97.82161,97.76736, 96.96324, 92.74496]
        },
        {
            name: 'redis_rc',
            type: 'line',
            data: [106.761557,106.8298, 103.45517,99.9996]
        },

        {
            name: 'java_rr',
            type: 'line',
            data: [130.0568,129,123.74628 , 116.9498]
        },
        {
            name: 'java_rc',
            type: 'line',
            data: [130.89789,130.75902, 128.7921, 119.9]
        },

    ]
};