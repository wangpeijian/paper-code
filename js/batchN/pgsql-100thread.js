option = {
    textStyle: {
        fontSize: 16,
    },
    color: ["#333", "#e01f54", '#e87c25', '#3fb1e3', '#6be6c1'],
    // title: {
    //     text: 'PostgreSQL事务吞吐量趋势',
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
        right: '15%',
        bottom: '3%',
        containLabel: true
    },

    xAxis: {
        axisLabel: {
            fontSize: 16,
        },
        type: 'category',
        boundaryGap: false,
        data: ['1', '10', '20'],
        name: '事务中锁定资源(个)'
    },
    yAxis: {
        axisLabel: {
            fontSize: 16,
        },
        type: 'value',
        min: 40,
        max: 280,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: '可串行化隔离级别',
            type: 'line',
            data: [267.39915, 112.4504, 67.98456]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',
            data: [228.09242, 97.76736, 63.6108]
        },
        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',
            data: [248.9, 106.8298, 65.31734]
        },

        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',
            data: [258.466, 129, 78.11776]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',
            data: [267.9114, 130.75902, 79.76185]
        },

    ]
};