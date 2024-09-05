import logging
from collections import defaultdict
from datetime import datetime

import matplotlib.pyplot as plt
import matplotlib.dates as mdates

logger = logging.getLogger(__name__)


class Stats:
    def __init__(self):
        self.data = defaultdict(list)

    def start_task_step(self, task_name: str):
        logger.info(f"Start task: {task_name}")
        self.data[task_name].append([datetime.now(), None])
        # default empty list

    def end_task_step(self, task_name: str):
        logger.info(f"End task: {task_name}")
        self.data[task_name][-1][1] = datetime.now()

    def draw(self):
        # Create figure and axis
        fig, ax = plt.subplots(figsize=(10, 6))

        # Define task positions and colors for clarity
        task_names = list(self.data.keys())
        colors = ['tab:blue', 'tab:orange', 'tab:green', 'tab:red', 'tab:purple']
        y_pos = range(len(task_names))  # Positions on y-axis for each task

        # Plot each task's execution times
        for i, (task, executions) in enumerate(self.data.items()):
            for start, end in executions:
                ax.barh(task, (end - start).total_seconds() / 3600, left=mdates.date2num(start),
                        color=colors[i % len(colors)])

        # Set axis labels and title
        ax.set_xlabel('Time')
        ax.set_ylabel('Task')
        ax.set_title('Task Execution Timeline')

        # Format x-axis to show time properly
        ax.xaxis_date()
        ax.xaxis.set_major_formatter(mdates.DateFormatter('%H:%M'))

        # Adjust x-axis limits to fit the timeline
        plt.xticks(rotation=45)
        plt.tight_layout()
        plt.show()
