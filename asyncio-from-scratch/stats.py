import logging
from collections import defaultdict
from datetime import datetime

import matplotlib.pyplot as plt
import matplotlib.dates as mdates

logger = logging.getLogger(__name__)


class Stats:
    def __init__(self):
        self.data = defaultdict(list)

    def plot_task_callback(self, task_name: str):
        logger.info(f"Start task: {task_name}")
        self.data[task_name].append([datetime.now()])

    def draw(self):
        fig, ax = plt.subplots(figsize=(10, 6))

        # Define colors for tasks
        colors = ['tab:blue', 'tab:orange', 'tab:green', 'tab:red', 'tab:purple']

        # Iterate through the data, plot a dot for each task execution
        for i, (task_name, times) in enumerate(self.data.items()):
            # Convert times into numerical format for x-axis (time)
            x = mdates.date2num(times)
            y = [i] * len(times)  # Y-axis values (constant for each task)

            # Plot the points
            ax.scatter(x, y, label=task_name, color=colors[i % len(colors)], s=1)

        # Formatting
        ax.set_xlabel('Execution Time')
        ax.set_ylabel('Task Name')
        ax.set_title('Task Execution Timeline')

        # Set task names on y-axis
        ax.set_yticks(range(len(self.data)))
        ax.set_yticklabels(self.data.keys())

        # Format x-axis for time
        ax.xaxis_date()
        ax.xaxis.set_major_formatter(mdates.DateFormatter('%H:%M:%S'))

        plt.xticks(rotation=45)
        plt.tight_layout()
        plt.show()
