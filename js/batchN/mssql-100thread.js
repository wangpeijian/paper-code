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
        data: ['1', '10', '20'],
        name: '下单商品（个）'
    },
    yAxis: {
        type: 'value',
        min: 40,
        max: 270,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: 'ssi',
            type: 'line',
            data: [238.4613,73.38786, 50.10096]
        },
        {
            name: 'redis_rr',
            type: 'line',
            data: [216.06164,99.0851,62.28765]
        },
        {
            name: 'redis_rc',
            type: 'line',
            data: [223.77976,100.33925, 65.49525]
        },
        {
            name: 'redis_ru',
            type: 'line',
            data: [228.53816, 107.10315,68.07411 ]
        },
        {
            name: 'java_rr',
            type: 'line',
            data: [250.68615 ,117.32014,80.40869 ]
        },
        {
            name: 'java_rc',
            type: 'line',
            data: [258.50409, 121.62024, 82.71384]
        },
        {
            name: 'java_ru',
            type: 'line',
            data: [261.60414,124.25424, 83.2524]
        },
    ]
};