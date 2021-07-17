option = {
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],

    title: {
        text: 'mysql-100并发'
    },

    tooltip: {
        trigger: 'axis'
    },

    legend: {
        data: ['ssi', 'java_ru', 'java_rc', 'java_rr', 'redis_ru', 'redis_rc', 'redis_rr']
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
        data: ['1.1', '1.2', '1.3', '1.4', '1.5'],
        name: 'zipf系数'
    },
    yAxis: {
        type: 'value',
        min: 20,
        max: 110,
        name: '吞吐量(qps)'
    },

    series: [
        {
            name: 'ssi',
            type: 'line',
            data: [79.62356, 64.992, 58.15224, 55.59571, 52.15872]
        },
        {
            name: 'java_ru',
            type: 'line',
            data: [90.56376, 74.42361, 66.28055, 63.57032, 59.84157]
        },
        {
            name: 'java_rc',
            type: 'line',
            data: [90.54564, 73.8668, 66.01464, 62.32456, 59.49268]
        },
        {
            name: 'java_rr',
            type: 'line',
            data: [88.70232, 72.48917, 65.49074, 61.19579, 56.28042]
        },
        {
            name: 'redis_ru',
            type: 'line',
            data: [81.9293, 67.3647, 59.5476, 56.68065, 54.23187]
        },

        {
            name: 'redis_rc',
            type: 'line',
            data: [81.9127, 66.79508, 59.40753, 56.58376, 54.18]
        },
        {
            name: 'redis_rr',
            type: 'line',
            data: [80.54028, 66.5788, 58.8729, 56.13041, 53.9504]
        },
    ]
};