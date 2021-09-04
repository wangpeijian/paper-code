option = {
    textStyle: {
        fontSize: 16,
    },
    color: ["#333", "#e01f54", '#fcce10', '#3fb1e3', '#c4ebad'],
    title: {
        text: 'pgsql-100并发'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['ssi', '读已提交隔离级别+Java锁', '可重复读隔离级别+Java锁', '读已提交隔离级别+Redis锁', '可重复读隔离级别+Redis锁']
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
        max: 100,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: 'ssi',
            type: 'line',

            data: [78.59313, 73.63335, 67.77024, 64.26522, 60.9402]
        },

        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',

            data: [94.2, 78.35085, 69.37938, 65.15469, 64.6443]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',

            data: [89.388, 76.52836, 67.67496, 64.79968, 64.207]
        },

        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',

            data: [83.1142, 71.16498, 62.9391, 58.64227, 56.5461]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',

            data: [67.74592, 55.41712, 49.55961, 47.3663, 45.81603]
        },
    ]
};