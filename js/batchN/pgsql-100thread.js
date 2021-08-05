option = {
    color: ["#333", "#e01f54", '#e87c25', '#3fb1e3', '#6be6c1'],
    title: {
        text: 'PostgreSQL事务吞吐量趋势',
        left: 'center',
    },

    tooltip: {
        trigger: 'axis'
    },

    legend: {
        top: ' 5%',
        data: ['s', 'redis_rr', 'redis_rc', 'java_rr', 'java_rc']
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
        data: ['1', '10', '20'],
        name: '下单商品（个）'
    },
    yAxis: {
        type: 'value',
        min: 40,
        max: 280,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: 's',
            type: 'line',
            data: [267.39915, 112.4504,77.19228 ]
        },
        {
            name: 'redis_rr',
            type: 'line',
            data: [228.09242,97.76736, 63.6108]
        },
        {
            name: 'redis_rc',
            type: 'line',
            data: [248.9,106.8298, 65.31734]
        },

        {
            name: 'java_rr',
            type: 'line',
            data: [258.466,129, 78.11776]
        },
        {
            name: 'java_rc',
            type: 'line',
            data: [267.9114,130.75902,  79.76185]
        },

    ]
};