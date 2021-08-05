option = {
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],

    title: {
        text: 'mysql-100并发'
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
        data: ['0.6', '0.8', '1.0', '1.2', '1.4'],
        name: 'zipf系数'
    },
    yAxis: {
        type: 'value',
        min: 50,
        max: 170,
        name: '吞吐量(qps)'
    },

    series: [
        {
            name: 'ssi',
            type: 'line',
            data: [133.947,120.74034, 95.43096, 63.09925, 51.62833]
        },
        {
            name: 'redis_rr',
            type: 'line',
            data: [142.88535, 134.6, 100.79736, 64.117, 53.53612]
        },
        {
            name: 'redis_rc',
            type: 'line',
            data: [143.7, 137.102, 104.3058, 65.7968, 56.79828]
        },
        {
            name: 'redis_ru',
            type: 'line',
            data: [143.1, 138.8, 104.84383, 66.77728, 55.93185]
        },
        {
            name: 'java_rr',
            type: 'line',
            data: [155.8, 150.42874, 121.2, 75.9, 62]
        },
        {
            name: 'java_rc',
            type: 'line',
            data: [157.8, 155.6, 124.4, 78.1, 64.9]
        },
        {
            name: 'java_ru',
            type: 'line',
            data: [157.75266, 157.28427, 126.3, 78.8, 66.2]
        },
    ]
};