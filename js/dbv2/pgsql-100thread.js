option = {
    textStyle: {
        fontSize: 16,
    },
    color: ["#333", "#e01f54", '#e87c25', '#3fb1e3', '#6be6c1'],
    // title: {
    //     text: 'PostgreSQL事务不同冲突系数的吞吐量趋势',
    //     left: 'center',
    // },

    tooltip: {
        trigger: 'axis'
    },

    legend: {
        top: ' 0%',
        data: ['可串行化隔离级别', '可重复读隔离级别+Redis锁', '读已提交隔离级别+Redis锁', '可重复读隔离级别+Java锁', '读已提交隔离级别+Java锁']
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
        data: ['0.6', '0.8', '1.0', '1.2', '1.4'],
        name: 'zipf系数'
    },
    yAxis: {
        axisLabel: {
            fontSize: 16,
        },
        type: 'value',
        min: 40,
        max: 170,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: '可串行化隔离级别',
            type: 'line',

            data: [159.61077, 152.133, 112.4504, 67.4287, 57.6477]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',

            data: [140.97333, 137.65515, 97.76736, 60.28965, 50.72036]
        },
        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',

            data: [145.39805, 140.7, 106.8298, 65.52006, 56.1975]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',

            data: [162.4748, 160.70865, 129, 79.79202, 67.39857]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',

            data: [166.7, 163, 130.75902, 83.11603, 69.5]
        },
    ]
};