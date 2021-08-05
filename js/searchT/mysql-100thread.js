option = {
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],

    title: {
        text: 'MySQL测试读事务对吞吐量的影响',
        left: 'center',
    },

    tooltip: {
        trigger: 'axis'
    },

    legend: {
        top: ' 5%',
        data: ['s', 'redis_rr', 'redis_rc', 'redis_ru', 'java_rr', 'java_rc', 'java_ru']
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
        min: 50,
        max: 130,
        name: '吞吐量(qps)'
    },

    series: [
        {
            name: 's',
            type: 'line',
            data: [100.983894,95.43096, 79.05632, 60.4469]
        },
        {
            name: 'redis_rr',
            type: 'line',
            data: [105.16093,100.85193, 94.46136, 77.3808]
        },
        {
            name: 'redis_rc',
            type: 'line',
            data: [105.86016,104.3058,  99.26917, 87.0941]
        },
        {
            name: 'redis_ru',
            type: 'line',
            data: [106.66089,104.76576,100.37208, 89.3031]
        },
        {
            name: 'java_rr',
            type: 'line',
            data: [126.2,121.2, 115.7, 87.63552]
        },
        {
            name: 'java_rc',
            type: 'line',
            data: [126.4,125.4, 117.8, 101.58984]
        },
        {
            name: 'java_ru',
            type: 'line',
            data: [127,126.3,118.7, 103.32405]
        },
    ]
};