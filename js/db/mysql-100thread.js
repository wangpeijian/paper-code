option = {
    textStyle: {
        fontSize: 16,
    },
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],

    title: {
        text: 'mysql-100并发'
    },

    tooltip: {
        trigger: 'axis'
    },

    legend: {
        data: ['ssi', '读未提交隔离级别+Java锁', '读已提交隔离级别+Java锁', '可重复读隔离级别+Java锁', '读未提交隔离级别+Redis锁', '读已提交隔离级别+Redis锁', '可重复读隔离级别+Redis锁']
    },

    grid: {
        top: '10%',
        left: '3%',
        right: '10%',
        bottom: '3%',
        containLabel: true
    },

    xAxis: {
        axisLabel: {
            fontSize: 16,
        },
        type: 'category',
        boundaryGap: false,
        data: ['1.1', '1.2', '1.3', '1.4', '1.5'],
        name: 'zipf系数'
    },
    yAxis: {
        axisLabel: {
            fontSize: 16,
        },
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
            name: '读未提交隔离级别+Java锁',
            type: 'line',
            data: [90.56376, 74.42361, 66.28055, 63.57032, 59.84157]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',
            data: [90.54564, 73.8668, 66.01464, 62.32456, 59.49268]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',
            data: [88.70232, 72.48917, 65.49074, 61.19579, 56.28042]
        },
        {
            name: '读未提交隔离级别+Redis锁',
            type: 'line',
            data: [81.9293, 67.3647, 59.5476, 56.68065, 54.23187]
        },

        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',
            data: [81.9127, 66.79508, 59.40753, 56.58376, 54.18]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',
            data: [80.54028, 66.5788, 58.8729, 56.13041, 53.9504]
        },
    ]
};